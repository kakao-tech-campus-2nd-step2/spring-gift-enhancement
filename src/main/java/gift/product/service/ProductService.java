package gift.product.service;

import gift.category.model.Category;
import gift.category.repository.CategoryRepository;
import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void save(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImgUrl(), category);
        productRepository.save(product);
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> new ProductDto(
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImgUrl(),
                        product.getCategoryId()));
    }

    public ProductDto findById(Long productId) {
        return productRepository.findById(productId)
                .map(product -> new ProductDto(
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImgUrl(),
                        product.getCategoryId()))
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
    }

    public void update(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
        product.update(productDto.getName(), productDto.getPrice(), productDto.getImgUrl(), category);
        productRepository.save(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

}