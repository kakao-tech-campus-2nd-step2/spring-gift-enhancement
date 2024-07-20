package gift.product;

import gift.category.CategoryRepository;
import gift.exception.InvalidCategory;
import gift.exception.InvalidProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product -> new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()));
    }

    public Optional<ProductResponseDto> getProductById(Long id) {
        return productRepository.findById(id)
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getId()));
    }

    public ProductResponseDto postProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.saveAndFlush(new Product(
            productRequestDto.name(),
            productRequestDto.price(),
            productRequestDto.url(),
            categoryRepository.findById(productRequestDto.categoryId()).orElseThrow()
        ));
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }

    public ProductResponseDto putProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new InvalidProduct("유효하지 않은 상품입니다"));

        product.update(productRequestDto.name(), productRequestDto.price(), productRequestDto.url());
        productRepository.saveAndFlush(product);

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }

    public ProductResponseDto putCategory(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new InvalidProduct("유효하지 않은 상품입니다"));

        product.changeCategory(categoryRepository.findById(categoryId)
            .orElseThrow(() -> new InvalidCategory("유효하지 않은 카테고리입니다")));

        productRepository.saveAndFlush(product);

        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }

    public HttpEntity<String> deleteProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new InvalidProduct("유효하지 않은 상품입니다"));

        productRepository.deleteById(id);

        return ResponseEntity.ok("성공적으로 삭제되었습니다");
    }

}
