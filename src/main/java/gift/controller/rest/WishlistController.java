package gift.controller.rest;

import gift.entity.Product;
import gift.entity.WishlistDTO;
import gift.service.WishlistService;
import gift.util.ResponseUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {


    private final WishlistService wishlistService;
    private final ResponseUtility responseUtility;

    @Autowired
    public WishlistController(WishlistService wishlistService, ResponseUtility responseUtility) {
        this.wishlistService = wishlistService;
        this.responseUtility = responseUtility;
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
        return ResponseEntity
                .ok()
                .body(responseUtility.makeResponse("added successfully"));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> deleteWishlist(@PathVariable("id") Long id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistService.deleteWishlist(email, id);
        return ResponseEntity
                .ok()
                .body(responseUtility.makeResponse("deleted successfully"));
    }
}
