package gift.controller.rest;

import gift.entity.Product;
import gift.entity.WishlistDTO;
import gift.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {


    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping()
    public List<Product> getWishlist(HttpServletRequest request,
                                     Pageable pageable) {
        String email = (String) request.getAttribute("email");
        return wishlistService.getWishlistProducts(email, pageable).getContent();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, String>> postWishlist(HttpServletRequest request, @RequestBody @Valid WishlistDTO form) {
        String email = (String) request.getAttribute("email");
        wishlistService.addWishlistProduct(email, form);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product added to wishlist");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> deleteWishlist(@PathVariable("id") Long id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistService.deleteWishlist(email, id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Wishlist deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
