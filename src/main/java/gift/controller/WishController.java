package gift.controller;

import gift.component.LoginMember;
import gift.dto.ApiResponse;
import gift.model.Member;
import gift.model.Wish;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Wish>>> getWishes(@LoginMember Member member) {
        List<Wish> wishes = wishService.getWishesByMemberId(member.getId());
        ApiResponse<List<Wish>> response = new ApiResponse<>(true, "Wishes retrieved successfully.", wishes, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Wish>> addWish(@RequestBody Wish wish, @LoginMember Member member) {
        wish.setMember(member);
        Wish savedWish = wishService.addWish(wish);
        ApiResponse<Wish> response = new ApiResponse<>(true, "Wish added successfully.", savedWish, null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeWish(@PathVariable Long id, @LoginMember Member member) {
        boolean removed = wishService.removeWish(id, member.getId());
        if (removed) {
            ApiResponse<Void> response = new ApiResponse<>(true, "Wish removed successfully.", null, null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<Void> response = new ApiResponse<>(false, "Failed to remove wish.", null, "500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
