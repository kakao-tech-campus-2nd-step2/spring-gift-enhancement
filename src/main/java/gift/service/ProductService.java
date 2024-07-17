package gift.service;

import gift.domain.category.Category;
import gift.domain.category.CategoryRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.dto.ProductChangeRequestDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gift.exception.ErrorCode.INVALID_CATEGORY;
import static gift.exception.ErrorCode.INVALID_PRODUCT;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addProduct(ProductRequestDto requestDto) {
        Category category = categoryRepository.findByName(requestDto.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CATEGORY));
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImgUrl(), category);
        productRepository.save(product);
    }

    public List<ProductResponseDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    public ProductResponseDto findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto editProduct(Long id, ProductChangeRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT));
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new CustomException(INVALID_CATEGORY));
        product.update(request.getName(), request.getPrice(), request.getImgUrl(), category);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkProductValidation(id);
        productRepository.deleteById(id);
    }

    private void checkProductValidation(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException(INVALID_PRODUCT);
        }
    }
}
