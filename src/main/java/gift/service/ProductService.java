package gift.service;

import gift.dto.product.request.CreateProductRequest;
import gift.dto.product.request.UpdateProductRequest;
import gift.dto.product.response.ProductResponse;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        return ProductMapper.toResponse(product);
    }

    @Transactional
    public Long createProduct(CreateProductRequest request) {
        return productRepository.save(ProductMapper.toProduct(request)).getId();
    }

    @Transactional
    public void updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        ProductMapper.updateProduct(product, request);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public void checkProductExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
    }
}
