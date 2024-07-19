package gift.service;

import gift.model.Product;
import gift.model.ProductOption;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductOptionService productOptionService;

    public ProductService(ProductRepository productRepository, ProductOptionService productOptionService) {
        this.productRepository = productRepository;
        this.productOptionService = productOptionService;
    }

    public void save(Product product) {
        validateProductOptions(product);
        productRepository.save(product);
    }

    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public void update(Product updatedProduct) {
        validateProductOptions(updatedProduct);
        productRepository.save(updatedProduct);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public void delete(Long id) {

        productOptionService.deleteByProductId(id);

        productRepository.deleteById(id);
    }

    private void validateProductOptions(Product product) {
        List<ProductOption> options = product.getOptions();
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("1개 이상의 옵션이 필요합니다.");
        }
    }
}