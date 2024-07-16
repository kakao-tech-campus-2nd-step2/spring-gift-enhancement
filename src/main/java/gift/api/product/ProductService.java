package gift.api.product;

import gift.api.category.Category;
import gift.api.category.CategoryRepository;
import gift.global.exception.NoSuchEntityException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getProducts(Pageable pageable) {
        Page<Product> allProducts = productRepository.findAll(pageable);
        return allProducts.hasContent() ? allProducts.getContent() : Collections.emptyList();
    }

    public Long add(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                                .orElseThrow(() -> new NoSuchEntityException("category"));
        Product product = new Product(category, productRequest.getName(),
                                productRequest.getPrice(), productRequest.getImageUrl());
        return productRepository.save(product).getId();
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("product"));
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NoSuchEntityException("category"));
        product.update(category, productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
