package gift.administrator.product;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryService;
import gift.administrator.option.Option;
import gift.administrator.option.OptionDTO;
import gift.administrator.option.OptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OptionService optionService;

    public ProductService(ProductRepository productRepository,
        CategoryService categoryService, OptionService optionService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.optionService = optionService;
    }

    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy, Direction direction) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<ProductDTO> products = productPage.stream()
            .map(ProductDTO::fromProduct)
            .toList();
        return new PageImpl<>(products, pageRequest, productPage.getTotalElements());
    }

    public List<String> getAllCategoryName() {
        return productRepository.findDistinctCategoryNamesWithProducts();
    }

    public ProductDTO getProductById(long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return ProductDTO.fromProduct(product);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public ProductDTO addProduct(ProductDTO productDTO) throws NotFoundException {
        validateOptions(productDTO);
        if (productRepository.existsByName(productDTO.getName())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        Category category = getCategoryById(productDTO.getCategoryId());
        Product product = productDTO.toProduct(productDTO, category);
        category.addProducts(product);
        Product savedProduct = productRepository.save(product);
        List<OptionDTO> optionList = new ArrayList<>();
        for (Option option : product.getOptions()) {
            option.setProduct(savedProduct);
            optionList.add(optionService.addOption(OptionDTO.fromOption(option), product));
        }
        savedProduct.setOption(
            optionList.stream().map(optionDTO -> optionDTO.toOption(product)).toList());
        return ProductDTO.fromProduct(savedProduct);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) throws NotFoundException {
        validateOptions(productDTO);
        Product existingProduct = productRepository.findById(productDTO.getId())
            .orElseThrow(NotFoundException::new);
        if (productRepository.existsByNameAndIdNot(productDTO.getName(), productDTO.getId())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        Category newCategory = categoryService.getCategoryById(productDTO.getCategoryId())
            .toCategory();
        Category oldCategory = existingProduct.getCategory();
        oldCategory.removeProducts(existingProduct);
        existingProduct.setCategory(newCategory);
        ArrayList<Option> options = productDTO.getOptions().stream()
            .map(optionDTO -> optionDTO.toOption(existingProduct))
            .collect(Collectors.toCollection(ArrayList::new));
        optionService.deleteOptionByProductId(existingProduct.getId());
        existingProduct.update(productDTO.getName(), productDTO.getPrice(),
            productDTO.getImageUrl(), newCategory, new ArrayList<>());
        for (Option option : options) {
            option.setProduct(existingProduct);
        }
        Product savedProduct = productRepository.save(existingProduct);
        List<OptionDTO> optionList = new ArrayList<>();
        for (Option option : options) {
            OptionDTO optionDTO = optionService.addOption(OptionDTO.fromOption(option),
                savedProduct);
            optionList.add(optionDTO);
        }
        savedProduct.setOption(
            optionList.stream().map(optionDTO -> optionDTO.toOption(savedProduct)).toList());
        return ProductDTO.fromProduct(savedProduct);
    }

    public void existsByNamePutResult(String name, BindingResult result) {
        if (existsByName(name)) {
            result.addError(new FieldError("productDTO", "name", "존재하는 이름입니다."));
        }
    }

    public void existsByNameAndIdPutResult(String name, long id, BindingResult result)
        throws NotFoundException {
        if (existsByName(name) && !Objects.equals(getProductById(id).getName(), name)) {
            result.addError(new FieldError("productDTO", "name", "존재하는 이름입니다."));
        }
    }

    public void deleteProduct(long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        optionService.deleteOptionByProductId(product.getId());
        product.getCategory().removeProducts(product);
        productRepository.deleteById(id);
    }

    public Category getCategoryById(long categoryId) throws NotFoundException {
        return categoryService.getCategoryById(categoryId).toCategory();
    }

    private void validateOptions(ProductDTO productDTO) {
        List<OptionDTO> optionsDTO = productDTO.getOptions();
        if (optionsDTO.isEmpty()) {
            throw new IllegalArgumentException("상품은 적어도 하나의 옵션이 있어야 합니다.");
        }
    }
}
