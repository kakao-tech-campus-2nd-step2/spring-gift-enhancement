package gift.product.service;

import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.validator.ProductNameValidator;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNameValidator productNameValidator;

    public ProductService(ProductRepository productRepository,
        ProductNameValidator productNameValidator) {
        this.productRepository = productRepository;
        this.productNameValidator = productNameValidator;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductResponse addProduct(@Valid ProductRequest productRequest) {
        Product product = ProductRequest.toEntity(productRequest);
        validateProduct(product);
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    public ProductResponse updateProduct(Long id, @Valid ProductRequest productRequest) {
        Product product = ProductRequest.toEntity(productRequest);
        product.updateId(id);
        validateProduct(product);
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private void validateProduct(Product product) {
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        productNameValidator.validate(product, result);
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }
    }

}
