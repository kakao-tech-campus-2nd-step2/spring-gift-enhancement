package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.CategoryNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.model.category.Category;
import gift.model.product.Product;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, WishRepository wishRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.categoryId()).orElseThrow(
            CategoryNotFoundException::new);
        Product product = productRepository.save(productRequest.toEntity(category));
        return ProductResponse.from(product);
    }

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return ProductResponse.from(product);
    }

    public PageResponse<ProductResponse> findAllProduct(Pageable pageable) {
        Page<Product> productList = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = productList.getContent().stream()
            .map(ProductResponse::from)
            .toList();

        return PageResponse.from(productResponses, productList);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        product.updateProduct(productRequest);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        wishRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }
}
