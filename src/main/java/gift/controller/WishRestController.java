package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.request.MemberRequest;
import gift.dto.request.WishRequest;
import gift.dto.response.WishResponseDto;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wishes")
public class WishRestController {
    private final WishService wishService;

    public WishRestController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@LoginMember MemberRequest memberRequest, @RequestBody WishRequest wishRequest){
        wishService.save(memberRequest, wishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<WishResponseDto>> getMemberWishes(@LoginMember MemberRequest memberRequest, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(wishService.getPagedMemberWishesByMemberId(memberRequest.id(),pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginMember MemberRequest memberRequest, @PathVariable Long id){
        wishService.deleteWishByMemberIdAndId(memberRequest.id(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQuantity(@LoginMember MemberRequest memberRequest, @PathVariable Long id, @RequestBody WishRequest wishRequest){
        wishService.updateQuantityByMemberIdAndId(memberRequest.id(), id, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
