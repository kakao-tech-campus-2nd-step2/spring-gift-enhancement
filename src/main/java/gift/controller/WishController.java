package gift.controller;

import gift.config.auth.LoginUser;
import gift.domain.model.entity.User;
import gift.domain.model.dto.WishResponseDto;
import gift.domain.model.dto.WishUpdateRequestDto;
import gift.domain.model.enums.WishSortBy;
import gift.service.WishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<WishResponseDto>> getWishes(@LoginUser User user,
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
        @RequestParam(defaultValue = "ID_DESC") WishSortBy sortBy) {
        return ResponseEntity.ok(wishService.getWishes(user.getEmail(), page, sortBy));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishResponseDto> addWish(@PathVariable Long productId,
        @LoginUser User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishService.addWish(user.getEmail(), productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<WishResponseDto> updateWish(
        @PathVariable Long productId,
        @Valid @RequestBody WishUpdateRequestDto wishUpdateRequestDto, @LoginUser User user) {
        return ResponseEntity.ok(wishService.updateWish(productId, user.getEmail(), wishUpdateRequestDto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long productId, @LoginUser User user) {
        wishService.deleteWish(user.getEmail(), productId);
        return ResponseEntity.noContent().build();
    }
}
