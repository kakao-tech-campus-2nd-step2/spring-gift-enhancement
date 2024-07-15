package gift.wish;

import gift.common.auth.LoginMember;
import gift.common.auth.LoginMemberDto;
import gift.wish.model.WishRequestDto;
import gift.wish.model.WishResponseDto;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<WishResponseDto>> getWishList(
        @LoginMember LoginMemberDto loginMemberDto,
        @PageableDefault(size = 10, sort = "product", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok()
            .body(wishService.getWishList(loginMemberDto, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> insertProductToWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        Long wishId = wishService.addProductToWishList(wishRequestDto, loginMemberDto);
        return ResponseEntity.created(URI.create("/api/wishes/" + wishId)).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateProductInWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.updateProductInWishList(wishRequestDto, loginMemberDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductInWishList(
        @RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.deleteProductInWishList(wishRequestDto, loginMemberDto);
        return ResponseEntity.ok().build();
    }
}
