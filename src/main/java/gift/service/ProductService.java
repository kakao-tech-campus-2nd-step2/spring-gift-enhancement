package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.InvalidProductDataException;
import gift.exception.ProductNotFoundException;
import gift.repository.category.CategorySpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
public class ProductService {
    private final ProductSpringDataJpaRepository productRepository;
    private final CategorySpringDataJpaRepository categoryRepository;

    @Autowired
    public ProductService(ProductSpringDataJpaRepository productRepository, CategorySpringDataJpaRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product register(ProductRequest productRequest) {
        Category category = categoryRepository.findByName(productRequest.getCategoryName()).orElseThrow(() -> new CategoryNotFoundException("해당 카테고리는 존재하지 않습니다."));

        Product product = new Product(productRequest, category);
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidProductDataException("상품 데이터가 유효하지 않습니다: " + e.getMessage(), e);
        }

    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findOne(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }

    public Product update(Long productId, ProductRequest productRequest) {
        Category category = categoryRepository.findByName(productRequest.getCategoryName()).
                orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        product.update(productRequest, category);
        return product;

    }

    public Product delete(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        productRepository.delete(product);
        return product;
    }
}
