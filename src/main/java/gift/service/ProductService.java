package gift.service;

import gift.dto.product.request.CreateProductRequest;
import gift.dto.product.request.UpdateProductRequest;
import gift.dto.product.response.ProductResponse;
import gift.entity.Category;
import gift.entity.Product;
import gift.exception.category.CategoryNotFoundException;
import gift.exception.product.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(CategoryNotFoundException::new);
        return productRepository.save(ProductMapper.toProduct(request, category)).getId();
    }

    @Transactional
    public void updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(CategoryNotFoundException::new);
        ProductMapper.updateProduct(product, request, category);
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
