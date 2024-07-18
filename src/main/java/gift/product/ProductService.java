package gift.product;

import gift.category.CategoryRepository;
import gift.category.model.Category;
import gift.common.exception.ProductException;
import gift.product.model.Product;
import gift.product.model.ProductRequestDto;
import gift.product.model.ProductResponseDto;
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
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(ProductResponseDto::from);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        return ProductResponseDto.from(productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product 값이 잘못되었습니다.")));
    }

    @Transactional
    public Long insertProduct(ProductRequestDto productRequestDto) throws ProductException {
        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("wish 가 잘못되었습니다."));
        Product product = new Product(productRequestDto.getName(), productRequestDto.getPrice(),
            productRequestDto.getImageUrl(), category);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void updateProductById(Long id, ProductRequestDto productRequestDto)
        throws ProductException {
        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category 값이 잘못되었습니다."));
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product 값이 잘못되었습니다."));
        product.updateInfo(productRequestDto.getName(), productRequestDto.getPrice(),
            productRequestDto.getImageUrl(), category);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
