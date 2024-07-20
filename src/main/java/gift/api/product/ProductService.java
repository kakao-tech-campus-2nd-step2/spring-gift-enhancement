package gift.api.product;

import gift.api.category.Category;
import gift.api.category.CategoryRepository;
import gift.api.product.dto.ProductRequest;
import gift.api.product.dto.ProductResponse;
import gift.global.exception.NoSuchEntityException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<ProductResponse> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        if (products.hasContent()) {
            return products.getContent()
                    .stream()
                    .map(ProductResponse::of)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Long add(ProductRequest productRequest) {
        Category category = findCategoryById(productRequest.categoryId());
        Product product = productRequest.toEntity(category);
        return productRepository.save(product).getId();
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        Category category = findCategoryById(productRequest.categoryId());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("product"));
        product.update(category,
                productRequest.name(),
                productRequest.price(),
                productRequest.imageUrl());
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NoSuchEntityException("category"));
    }
}
