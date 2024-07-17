package gift.product.application;

import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.product.dao.CategoryRepository;
import gift.product.dao.OptionRepository;
import gift.product.dao.ProductRepository;
import gift.product.dto.OptionRequest;
import gift.product.dto.OptionResponse;
import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.entity.Category;
import gift.product.entity.Product;
import gift.product.util.OptionMapper;
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
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
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
        Product product = productRepository.save(ProductMapper.toEntity(request, category));

        optionRepository.save(OptionMapper.toEntity(request.option(), product));

        return ProductMapper.toResponseDto(product);
    }

    @Transactional
    public OptionResponse addOptionToProduct(Long id, OptionRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return OptionMapper.toResponseDto(
                optionRepository.save(OptionMapper.toEntity(request, product))
        );
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteOptionFromProduct(Long id, OptionRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getOptions()
                   .size() == 1) {
            throw new CustomException(ErrorCode.OPTION_REMOVE_FAILED);
        }

        optionRepository.deleteByProduct_IdAndName(id, request.name());
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