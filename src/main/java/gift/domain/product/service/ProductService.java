package gift.domain.product.service;

import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        return entityToDto(product);
    }

    public Page<ProductResponse> getAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable).map(this::entityToDto);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Category savedCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();
        Product product = productRepository.save(dtoToEntity(productRequest, savedCategory));
        return entityToDto(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        Category savedCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();

        product.updateAll(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl(), savedCategory);

        return entityToDto(productRepository.save(product));

    }

    public void deleteProduct(Long id) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        productRepository.delete(product);
    }

    private ProductResponse entityToDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategory().getId());
    }

    private Product dtoToEntity(ProductRequest productRequest, Category category) {

        return new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl(), category);
    }

}
