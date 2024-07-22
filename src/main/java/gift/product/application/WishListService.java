package gift.product.application;

import gift.product.domain.Product;
import gift.product.domain.ProductOption;
import gift.product.domain.WishList;
import gift.product.domain.WishListProduct;
import gift.product.exception.ProductException;
import gift.product.infra.ProductRepository;
import gift.product.infra.WishListRepository;
import gift.user.application.UserService;
import gift.user.domain.User;
import gift.util.ErrorCode;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository,
                           UserService userService) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    public Page<WishList> getProductsInWishList(Long userId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (wishListRepository.findByUserId(userId, pageable).isEmpty()) {
            throw new ProductException(ErrorCode.WISHLIST_NOT_FOUND);
        }
        return wishListRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public void addProductToWishList(Long userId, Long wishlistId, Long productId, Long optionId) {
        WishList wishList = findById(wishlistId);
        if (!Objects.equals(wishList.getUser().getId(), userId)) {
            throw new ProductException(ErrorCode.NOT_USER_OWNED);
        }

        Product product = productRepository.findById(productId);
        ProductOption productOption = productRepository.getProductWithOption(productId, optionId);

        wishList.addWishListProduct(new WishListProduct(wishList, product, productOption));


        wishListRepository.save(wishList);
    }

    public WishList findById(Long id) {
        return wishListRepository.findById(id).orElseThrow(() -> new ProductException(ErrorCode.WISHLIST_NOT_FOUND));
    }

    @Transactional
    public void deleteProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList != null) {
            wishList.removeWishListProduct(productId);
        } else {
            throw new ProductException(ErrorCode.WISHLIST_NOT_FOUND);
        }
    }

    public void createWishList(Long userId) {
        if (wishListRepository.findByUserId(userId) != null) {
            throw new ProductException(ErrorCode.WISHLIST_ALREADY_EXISTS);
        }
        User user = userService.getUser(userId);

        WishList wishList = new WishList(user, LocalDateTime.now());
        wishListRepository.save(wishList);
    }
}
