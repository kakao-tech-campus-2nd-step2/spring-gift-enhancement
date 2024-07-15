package gift.product;

import gift.common.exception.ProductException;
import gift.product.model.Product;
import gift.product.model.ProductRequestDto;
import gift.product.model.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public void insertProduct(ProductRequestDto productRequestDto) throws ProductException {
        productRepository.save(productRequestDto.toEntity());
    }

    @Transactional
    public void updateProductById(Long id, ProductRequestDto productRequestDto)
        throws ProductException {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product 값이 잘못되었습니다."));
        product.updateInfo(productRequestDto.toEntity());
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
