package gift.product.service;

import gift.product.domain.Product;
import gift.product.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(@Valid Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, @Valid Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
