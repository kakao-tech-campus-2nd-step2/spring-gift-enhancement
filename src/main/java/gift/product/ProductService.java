package gift.product;

import gift.category.Category;
import gift.category.CategoryDTO;
import gift.category.CategoryService;
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

    public ProductService(ProductRepository productRepository,
        CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
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

    public List<String> getAllCategory() {
        return productRepository.findAll().stream()
            .map(Product::getCategory).map(CategoryDTO::fromCategory).map(CategoryDTO::getName).toList();
    }

    public ProductDTO getProductById(long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return ProductDTO.fromProduct(product);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public void addProduct(ProductDTO product) throws NotFoundException {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        CategoryDTO categoryDTO = categoryService.getCategoryById(product.getCategoryId());
        Category category = categoryDTO.toCategory();
        category.addProducts(toProduct(product));
        productRepository.save(toProduct(product));
    }

    public void updateProduct(ProductDTO productDTO) throws NotFoundException {
        Product product = productRepository.findById(productDTO.getId())
            .orElseThrow(NotFoundException::new);
        if (productRepository.existsByName(productDTO.getName())
            && !Objects.equals(product.getId(),
            productRepository.findByName(productDTO.getName()).getId())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        Category newCategory = categoryService.getCategoryById(productDTO.getCategoryId())
            .toCategory();
        /*Category oldCategory = product.getCategory();

        if (!oldCategory.equals(newCategory)) {
            oldCategory.removeProducts(product);
            newCategory.addProducts(product);
        }*/
        product.update(productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl(),
            newCategory);
        productRepository.save(product);
    }

    public void existsByNamePutResult(String name, BindingResult result)
        throws NotFoundException {
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
        product.getCategory().removeProducts(product);
        productRepository.deleteById(id);
    }

    public Product toProduct(ProductDTO productDTO) throws NotFoundException {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getPrice(),
            productDTO.getImageUrl(),
            categoryService.getCategoryById(productDTO.getCategoryId()).toCategory());
    }
}
