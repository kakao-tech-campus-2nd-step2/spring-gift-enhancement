package gift.service;

import static gift.util.constants.CategoryConstants.CATEGORY_NOT_FOUND;
import static gift.util.constants.OptionConstants.OPTION_NOT_FOUND;
import static gift.util.constants.ProductConstants.INVALID_PRICE;
import static gift.util.constants.ProductConstants.PRODUCT_NOT_FOUND;

import gift.dto.product.ProductCreateRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.ProductUpdateRequest;
import gift.exception.product.InvalidProductPriceException;
import gift.exception.product.ProductNotFoundException;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    // 모든 상품 조회 (페이지네이션)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductService::convertToDTO);
    }

    // ID로 상품 조회
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductService::convertToDTO)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
    }

    // 상품 추가
    public ProductResponse addProduct(ProductCreateRequest productCreateRequest) {
        validatePrice(productCreateRequest.price());

        Category category = categoryRepository.findById(productCreateRequest.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(
                CATEGORY_NOT_FOUND + productCreateRequest.categoryId()));

        List<Option> options = productCreateRequest.options().stream()
            .map(optionId -> optionRepository.findById(optionId)
                .orElseThrow(() -> new ProductNotFoundException(OPTION_NOT_FOUND + optionId)))
            .toList();

        Product product = convertToEntity(productCreateRequest, category, options);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // 상품 수정
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        validatePrice(productUpdateRequest.price());

        Category category = categoryRepository.findById(productUpdateRequest.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(
                CATEGORY_NOT_FOUND + productUpdateRequest.categoryId()));

        List<Option> options = productUpdateRequest.options().stream()
            .map(optionId -> optionRepository.findById(optionId)
                .orElseThrow(() -> new ProductNotFoundException(OPTION_NOT_FOUND + optionId)))
            .toList();

        product.update(productUpdateRequest.name(), productUpdateRequest.price(),
            productUpdateRequest.imageUrl(), category, options);
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND + id);
        }
        productRepository.deleteById(id);
    }

    private static void validatePrice(int price) {
        if (price < 0) {
            throw new InvalidProductPriceException(INVALID_PRICE);
        }
    }

    // Mapper methods
    private static ProductResponse convertToDTO(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategoryId(),
            product.getOptions().stream()
                .map(Option::getId)
                .toList()
        );
    }

    private static Product convertToEntity(ProductCreateRequest productCreateRequest,
        Category category, List<Option> options) {
        return new Product(
            null,
            productCreateRequest.name(),
            productCreateRequest.price(),
            productCreateRequest.imageUrl(),
            category,
            options
        );
    }

    public Product convertToEntity(ProductResponse productResponse) {
        Category category = categoryRepository.findById(productResponse.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(
                CATEGORY_NOT_FOUND + productResponse.categoryId()));

        List<Option> options = productResponse.options().stream()
            .map(optionId -> optionRepository.findById(optionId)
                .orElseThrow(() -> new ProductNotFoundException(OPTION_NOT_FOUND + optionId)))
            .toList();

        return new Product(
            productResponse.id(),
            productResponse.name(),
            productResponse.price(),
            productResponse.imageUrl(),
            category,
            options
        );
    }
}
