package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository,CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product createProduct(CreateProductDto productDto) {
        Product product = productDto.toProduct();
        productRepository.save(product);
        return product;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return product;
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow();
        productDto.updateProduct(product);
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}
