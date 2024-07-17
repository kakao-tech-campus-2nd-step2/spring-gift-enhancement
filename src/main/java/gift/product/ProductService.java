package gift.product;

import static gift.exception.ErrorMessage.CATEGORY_NOT_FOUND;
import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;

import gift.category.Category;
import gift.category.CategoryRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void addProduct(ProductDTO productDTO) {
        Category findCategory = categoryRepository.findByName(productDTO.category().getName())
            .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));

        productRepository.save(productDTO.toEntity(findCategory));
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        Category findCategory = categoryRepository.findByName(productDTO.category().getName())
            .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));

        productRepository.findById(id)
            .ifPresentOrElse(
                e -> productRepository.save(productDTO.toEntity(id, findCategory)),
                () -> {
                    throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
                }
            );
    }

    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
