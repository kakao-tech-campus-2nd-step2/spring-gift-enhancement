package gift.domain.product.service;

import gift.domain.product.dto.ProductResponse;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductReadAllResponse;
import gift.domain.product.entity.Category;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductJpaRepository;
import gift.domain.wishlist.service.WishlistService;
import gift.exception.InvalidProductInfoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final OptionService optionService;
    private final CategoryService categoryService;
    private final WishlistService wishlistService;

    public ProductService(
        ProductJpaRepository productJpaRepository,
        OptionService optionService,
        CategoryService categoryService,
        WishlistService wishlistService) {
        this.productJpaRepository = productJpaRepository;
        this.optionService = optionService;
        this.categoryService = categoryService;
        this.wishlistService = wishlistService;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Category category = categoryService.readById(productRequest.categoryId());
        Product product = productRequest.toProduct(category);

        Product savedProduct = productJpaRepository.save(product);
        optionService.create(product, productRequest.options());
        return ProductResponse.from(savedProduct);
    }

    public Page<ProductReadAllResponse> readAll(Pageable pageable) {
        Page<Product> foundProducts = productJpaRepository.findAll(pageable);

        if (foundProducts == null) {
            return Page.empty(pageable);
        }
        return foundProducts.map(ProductReadAllResponse::from);
    }

    public ProductResponse readById(long productId) {
        Product foundProduct = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        return ProductResponse.from(foundProduct);
    }

    public ProductResponse update(long productId, ProductRequest productRequest) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        Category category = categoryService.readById(productRequest.categoryId());

        product.updateInfo(category, productRequest.name(), productRequest.price(), productRequest.imageUrl());
        optionService.update(product, productRequest.options());

        Product savedProduct = productJpaRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    public void delete(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        wishlistService.deleteAllByProductId(productId);
        optionService.deleteAllByProduct(product);
        productJpaRepository.delete(product);
    }
}
