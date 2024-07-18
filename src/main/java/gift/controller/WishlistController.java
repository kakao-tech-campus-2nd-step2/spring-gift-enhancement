package gift.controller;

import gift.dto.ProductDTO;
import gift.dto.WishlistDTO;
import gift.model.User;
import gift.security.LoginMember;
import gift.service.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addWishlist(@PathVariable("productId") Long id, @LoginMember User user) {
        WishlistDTO wishlistDTO = new WishlistDTO(user.getEmail(), id);
        wishlistService.addWishlist(wishlistDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getWishlist(@LoginMember User user,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size) {
        Page<ProductDTO> products = wishlistService.getProductsFromWishlist(user.getEmail(), page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable("productId") Long id, @LoginMember User user) {
        wishlistService.deleteWishlist(user.getEmail(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}