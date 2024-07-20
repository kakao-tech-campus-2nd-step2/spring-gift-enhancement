package gift.administrator.product;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryService;
import gift.administrator.option.Option;
import gift.administrator.option.OptionDTO;
import gift.administrator.option.OptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void existsByNameThrowException(String name){
        if (existsByName(name)) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
    }
    public ProductDTO addProduct(ProductDTO productDTO) throws NotFoundException {
        existsByNameThrowException(productDTO.getName());
        Category category = getCategoryById(productDTO.getCategoryId());
        Product product = productDTO.toProduct(productDTO, category);
        category.addProducts(product);
        Product savedProduct = productRepository.save(product);
        addOptionsWhenAddingProduct(product.getOptions(), savedProduct, product);
        return ProductDTO.fromProduct(savedProduct);
    }

    public void addOptionsWhenAddingProduct(List<Option> options, Product settingProduct,
        Product addingProduct) {
        List<OptionDTO> optionList = new ArrayList<>();
        for (Option option : options) {
            option.setProduct(settingProduct);
            optionList.add(optionService.addOption(OptionDTO.fromOption(option), addingProduct));
        }
        settingProduct.setOption(optionDTOListToOptionList(optionList, addingProduct));
    }

    public ProductDTO updateProduct(ProductDTO productDTO) throws NotFoundException {
        Product existingProduct = productRepository.findById(productDTO.getId())
            .orElseThrow(NotFoundException::new);
        existsByNameAndIdNotThrowException(productDTO.getName(), productDTO.getId());
        Category newCategory = updateCategory(productDTO.getCategoryId(), existingProduct);
        List<Option> options = updateOptionWhenUpdateProduct(productDTO.getOptions(), existingProduct);
        existingProduct.update(productDTO.getName(), productDTO.getPrice(),
            productDTO.getImageUrl(), newCategory, new ArrayList<>());
        optionSetProductWhenProductUpdate(options, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        productAddOptionWhenProductUpdate(options, savedProduct);
        return ProductDTO.fromProduct(savedProduct);
    }

    public void existsByNameAndIdNotThrowException(String name, long productId){
        if (productRepository.existsByNameAndIdNot(name, productId)) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
    }

    public List<Option> updateOptionWhenUpdateProduct(List<OptionDTO> optionDTOList, Product product){
        List<Option> options = optionDTOListToOptionList(optionDTOList, product);
        optionService.deleteOptionByProductId(product.getId());
        return options;
    }

    public void optionSetProductWhenProductUpdate(List<Option> options, Product product){
        for (Option option : options) {
            option.setProduct(product);
        }
    }

    public void productAddOptionWhenProductUpdate(List<Option> options, Product product){
        List<OptionDTO> optionList = new ArrayList<>();
        for (Option option : options) {
            OptionDTO optionDTO = optionService.addOption(OptionDTO.fromOption(option),
                product);
            optionList.add(optionDTO);
        }
        product.setOption(optionDTOListToOptionList(optionList, product));
    }

    public List<Option> optionDTOListToOptionList(List<OptionDTO> optionList, Product product) {
        return optionList.stream().map(optionDTO -> optionDTO.toOption(product)).toList();
    }

    public Category updateCategory(long categoryId, Product product) throws NotFoundException {
        Category newCategory = getCategoryById(categoryId);
        Category oldCategory = product.getCategory();
        oldCategory.removeProducts(product);
        product.setCategory(newCategory);
        return newCategory;
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
}
