package gift.domain.product.service;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Category;
import gift.domain.product.entity.Product;
import gift.domain.wishlist.service.WishlistService;
import gift.exception.InvalidProductInfoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final CategoryService categoryService;
    private final WishlistService wishlistService;

    public ProductService(ProductJpaRepository productJpaRepository,
        CategoryService categoryService,
        WishlistService wishlistService) {
        this.productJpaRepository = productJpaRepository;
        this.categoryService = categoryService;
        this.wishlistService = wishlistService;
    }

    public Product create(ProductDto productDto) {
        Category category = categoryService.readById(productDto.categoryId());
        Product product = productDto.toProduct(category);
        return productJpaRepository.save(product);
    }

    public Page<Product> readAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    public Product readById(long productId) {
        return productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
    }

    public Product update(long productId, ProductDto productDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        Category category = categoryService.readById(productDto.categoryId());
        product.updateInfo(category, productDto.name(), productDto.price(), productDto.imageUrl());
        return productJpaRepository.save(product);
    }

    public void delete(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        wishlistService.deleteAllByProductId(productId);
        productJpaRepository.delete(product);
    }
}
