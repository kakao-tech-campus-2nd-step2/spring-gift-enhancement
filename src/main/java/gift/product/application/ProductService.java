package gift.product.application;

import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.product.dao.CategoryRepository;
import gift.product.dao.ProductRepository;
import gift.product.dto.OptionResponse;
import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.entity.Category;
import gift.product.entity.Product;
import gift.product.util.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponse> getPagedProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponseDto);
    }

    public ProductResponse getProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponseDto)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public Set<OptionResponse> getProductOptionsByIdOrThrow(Long id) {
        return getProductByIdOrThrow(id).options();
    }

    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findByName(request.categoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        return ProductMapper.toResponseDto(
                productRepository.save(ProductMapper.toEntity(request, category))
        );
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findByName(request.categoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        product.update(request, category);
    }

}