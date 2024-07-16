package gift.controller;

import gift.entity.Member;
import gift.dto.WishResponse;
import gift.dto.WishRequest;
import gift.service.WishService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<WishResponse> addWish(Member member, @RequestBody WishRequest request) {
        WishResponse wishResponse = wishService.addWish(member, request);
        return ResponseEntity.ok(wishResponse);
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(Member member) {
        List<WishResponse> wishes = wishService.getWishes(member);
        return ResponseEntity.ok(wishes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Slice<WishResponse>> getWishesPaged(Member member,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<WishResponse> responseSlice = wishService.getWishes(member, pageable)
            .map(WishResponse::from);
        return ResponseEntity.ok(responseSlice);
    }

    @DeleteMapping("/prooductId/{productId}")
    public ResponseEntity<Void> deleteWishByProductName(Member member,
        @PathVariable Long productId) {
        wishService.deleteWishByProductId(member, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishById(@PathVariable Long id) {
        wishService.deleteWishById(id);
        return ResponseEntity.noContent().build();
    }

}
