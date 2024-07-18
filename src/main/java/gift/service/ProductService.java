package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.exception.CategoryNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.request.ProductRequest;
import gift.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponse> getProducts(Pageable pageable) {
        List<ProductResponse> response = productRepository.findAll(pageable).stream()
                .map(Product::toDto)
                .toList();

        return new PageImpl<>(response, pageable, response.size());
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new)
                .toDto();
    }

    public void addProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(request.getName(), request.getPrice(), request.getImageUrl(), category);

        productRepository.save(product);
    }

    @Transactional
    public void editProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.changeName(request.getName());
        product.changePrice(request.getPrice());
        product.changeImageUrl(request.getImageUrl());
        product.changeCategory(category);
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());
    }

}
