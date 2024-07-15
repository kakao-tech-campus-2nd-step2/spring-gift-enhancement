package gift.wish.controller;

import gift.wish.domain.WishlistItem;
import gift.wish.service.WishlistService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wish")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<WishlistItem>> getWishlist(@PathVariable("id") Long userId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishlistItem> wishlist = wishlistService.getWishlistByUserId(userId, pageable);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<List<WishlistItem>> createWishlist(@PathVariable("id") Long userId, @RequestBody List<WishlistItem> wishlistItems) {
        List<WishlistItem> savedItems = wishlistService.saveWishlistItemsWithUserId(userId, wishlistItems);
        return new ResponseEntity<>(savedItems, HttpStatus.CREATED);
    }
}
