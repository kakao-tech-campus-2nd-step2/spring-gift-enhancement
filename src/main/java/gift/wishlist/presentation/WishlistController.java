package gift.wishlist.presentation;

import gift.wishlist.application.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("")
    public void add(@RequestAttribute("memberId") Long memberId, @RequestParam("productId") Long productId) {
        wishlistService.save(memberId, productId);
    }

    @GetMapping("")
    public ResponseEntity<Page<WishlistControllerResponse>> findAll(
            @RequestAttribute("memberId") Long memberId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                wishlistService.findAllByMemberId(memberId, pageable).map(WishlistControllerResponse::from)
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Page<WishlistControllerResponse>> findAllByProductId(
            @PathVariable("productId") Long productId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                wishlistService.findAllByProductId(productId, pageable).map(WishlistControllerResponse::from)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long wishlistId) {
        wishlistService.delete(wishlistId);
    }
}
