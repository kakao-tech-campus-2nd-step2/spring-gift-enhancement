package gift.service;

import static gift.util.Constants.CATEGORY_NOT_FOUND;
import static gift.util.Constants.INVALID_PRICE;
import static gift.util.Constants.PRODUCT_NOT_FOUND;

import gift.dto.product.ProductRequest;
import gift.dto.product.ProductResponse;
import gift.exception.product.InvalidProductPriceException;
import gift.exception.product.ProductNotFoundException;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    public ProductResponse addProduct(ProductRequest productRequest) {
        validatePrice(productRequest.price());
        Category category = categoryRepository.findById(productRequest.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(CATEGORY_NOT_FOUND + productRequest.categoryId()));
        Product product = convertToEntity(productRequest, category);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // 상품 수정
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        validatePrice(productRequest.price());

        Category category = categoryRepository.findById(productRequest.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(CATEGORY_NOT_FOUND + productRequest.categoryId()));

        product.update(productRequest.name(), productRequest.price(), productRequest.imageUrl(), category);
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
            product.getCategoryName()
        );
    }

    private static Product convertToEntity(ProductRequest productRequest, Category category) {
        return new Product(
            productRequest.id(),
            productRequest.name(),
            productRequest.price(),
            productRequest.imageUrl(),
            category
        );
    }

    public Product convertToEntity(ProductResponse productResponse) {
        Category category = categoryRepository.findById(productResponse.categoryId())
            .orElseThrow(() -> new ProductNotFoundException(CATEGORY_NOT_FOUND + productResponse.categoryId()));
        return new Product(
            productResponse.id(),
            productResponse.name(),
            productResponse.price(),
            productResponse.imageUrl(),
            category
        );
    }
}
