package gift.controller;

import gift.dto.WishRequest;
import gift.entity.Wish;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wish")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Wish>> getWishes(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            Pageable pageable) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        Page<Wish> wishes = wishService.getWishes(pageable);
        return ResponseEntity.ok(wishes);
    }

    @PostMapping("/add")
    public ResponseEntity<Wish> addWish(@RequestBody WishRequest wishRequest, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        Wish wish = wishService.addWish(token, wishRequest);
        return ResponseEntity.ok(wish);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeWish(@RequestBody WishRequest wishRequest, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        wishService.removeWish(token, wishRequest.getProductId());
        return ResponseEntity.ok().build();
    }
}