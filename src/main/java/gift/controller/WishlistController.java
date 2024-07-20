package gift.controller;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.service.WishlistService;
import gift.util.JwtTokenProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WishlistController(WishlistService wishlistService, JwtTokenProvider jwtTokenProvider) {
        this.wishlistService = wishlistService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getWishlistItems(
            @RequestParam("email") String email,
            Pageable pageable) {
        Page<Product> productPage = wishlistService.getWishlistByEmail(email, pageable);
        Map<String, Object> response = new HashMap<>();
        var data = productPage.getContent();

        List<ProductDto> productDtos = data.stream().map(v -> {
            ProductDto dto = new ProductDto(v);
            dto.setCategoryId(v.getCategory().getId());
            dto.setCategoryName(v.getCategory().getName());
            return dto;
        }).collect(Collectors.toList());

        response.put("content", productDtos);
        response.put("currentPage", productPage.getNumber() + 1);
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlistItem(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtTokenProvider.getEmail(token.substring(7));
        wishlistService.deleteWishlistItem(email, productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}")

    public ResponseEntity<Void> addWishlistItem( @RequestParam("email") String email, @RequestParam("optionId") Long optionId, @PathVariable Long productId) {
        wishlistService.addWishlistItem(email, optionId, productId);

        return ResponseEntity.ok().build();
    }

}
