package gift.controller;

import gift.dto.WishDto;
import gift.service.JwtUtil;
import gift.service.WishlistService;
import gift.vo.Wish;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gift.service.JwtUtil.getBearerToken;

@RestController
public class WishlistController {

    private final WishlistService service;
    private final JwtUtil jwtUtil;

    public WishlistController(WishlistService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/wishlist")
    public ResponseEntity<Map<String, Object>> getWishProductList(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        String token = getBearerToken(authorizationHeader);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        Page<Wish> allWishlistsPaged = service.getWishProductList(memberId, pageNumber-1, pageSize);
        List<WishDto> WishDtos = allWishlistsPaged.getContent().stream()
                .map(WishDto::toWishDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", WishDtos);
        response.put("totalPages", allWishlistsPaged.getTotalPages());
        response.put("currentPageNumber", allWishlistsPaged.getNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<Void> addToWishlist(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = getBearerToken(authorizationHeader);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        service.addWishProduct(memberId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/wishlist/{wishProductId}")
    public ResponseEntity<Void> deleteToWishlist(@PathVariable("wishProductId") Long wishProductId, @RequestHeader("Authorization") String authorizationHeader) {
        service.deleteWishProduct(wishProductId);

        return ResponseEntity.ok().build();
    }

}
