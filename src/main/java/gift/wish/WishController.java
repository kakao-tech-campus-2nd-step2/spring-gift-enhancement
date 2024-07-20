package gift.wish;

import gift.common.auth.LoginMember;
import gift.common.auth.LoginMemberDto;
import gift.common.model.PageResponseDto;
import gift.wish.model.WishRequest;
import gift.wish.model.WishResponse;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<WishResponse>> getWishList(
        @LoginMember LoginMemberDto loginMemberDto,
        @PageableDefault(size = 10, sort = "product", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
            PageResponseDto.of(wishService.getWishList(loginMemberDto, pageable), pageable));
    }

    @PostMapping
    public ResponseEntity<Void> insertProductToWishList(@RequestBody WishRequest wishRequest,
        @LoginMember LoginMemberDto loginMemberDto) {
        Long wishId = wishService.addProductToWishList(wishRequest, loginMemberDto);
        return ResponseEntity.created(URI.create("/api/wishes/" + wishId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductInWishList(
        @PathVariable("id") Long wishId,
        @RequestBody WishRequest wishRequest,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.updateProductInWishList(wishId, wishRequest, loginMemberDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductInWishList(
        @PathVariable("id") Long wishId,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.deleteProductInWishList(wishId, loginMemberDto);
        return ResponseEntity.ok().build();
    }
}
