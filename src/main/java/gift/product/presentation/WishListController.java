package gift.product.presentation;


import gift.product.application.WishListService;
import gift.product.domain.WishList;
import gift.util.CommonResponse;
import gift.util.annotation.JwtAuthenticated;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @JwtAuthenticated
    @GetMapping("/{userId}")
    public ResponseEntity<?> getWishList(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy,
                                         @RequestParam(defaultValue = "asc") String direction) {
        Page<WishList> products = wishListService.getProductsInWishList(userId, page, size, sortBy, direction);
        return ResponseEntity.ok(new CommonResponse<>(products, "위시리스트 조회 성공", true));
    }

    @JwtAuthenticated
    @PostMapping("/{userId}/create")
    public ResponseEntity<?> createWishList(@PathVariable Long userId) {
        wishListService.createWishList(userId);
        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트 생성 성공", true));
    }

    @JwtAuthenticated
    @PostMapping("/{wishListId}/add/{productId}/{optionId}/{quentity}")
    public ResponseEntity<?> addProductToWishList(@PathVariable Long wishListId, @PathVariable Long productId, @PathVariable Long optionId, @PathVariable Long quentity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        wishListService.addProductToWishList(userId, wishListId, productId, optionId, quentity);

        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트에 제품 추가 성공", true));
    }

    @JwtAuthenticated
    @DeleteMapping("/{userId}/delete/{productId}")
    public ResponseEntity<?> deleteProductFromWishList(@PathVariable Long userId, @PathVariable Long productId) {
        wishListService.deleteProductFromWishList(userId, productId);
        return ResponseEntity.ok(new CommonResponse<>(null, "제품 삭제 성공", true));
    }
}
