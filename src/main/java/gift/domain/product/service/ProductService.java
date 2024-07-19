package gift.domain.product.service;

import gift.domain.product.dto.ProductDetailResponseDto;
import gift.domain.product.dto.ProductRequestDto;
import gift.domain.product.dto.ProductResponseDto;
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

    public ProductDetailResponseDto create(ProductRequestDto productRequestDto) {
        Category category = categoryService.readById(productRequestDto.categoryId());
        Product product = productRequestDto.toProduct(category);

        Product savedProduct = productJpaRepository.save(product);
        optionService.create(product, productRequestDto.options());
        return ProductDetailResponseDto.from(savedProduct);
    }

    public Page<ProductResponseDto> readAll(Pageable pageable) {
        Page<Product> foundProducts = productJpaRepository.findAll(pageable);

        if (foundProducts == null) {
            return Page.empty(pageable);
        }
        return foundProducts.map(ProductResponseDto::from);
    }

    public ProductDetailResponseDto readById(long productId) {
        Product foundProduct = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        return ProductDetailResponseDto.from(foundProduct);
    }

    public ProductDetailResponseDto update(long productId, ProductRequestDto productRequestDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        Category category = categoryService.readById(productRequestDto.categoryId());

        product.updateInfo(category, productRequestDto.name(), productRequestDto.price(), productRequestDto.imageUrl());
        optionService.update(product, productRequestDto.options());

        Product savedProduct = productJpaRepository.save(product);
        return ProductDetailResponseDto.from(savedProduct);
    }

    public void delete(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        wishlistService.deleteAllByProductId(productId);
        optionService.deleteAllByProduct(product);
        productJpaRepository.delete(product);
    }
}
