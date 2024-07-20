package gift.controller;

import gift.ArgumentResolver.LoginMember;
import gift.dto.MemberDTO;
import gift.dto.WishListDTO;
import gift.dto.WishListRequest;
import gift.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/wishlist")
@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<WishListDTO> getWishList(@LoginMember MemberDTO memberDTO) {
        return ResponseEntity.ok(wishListService.getWishList(memberDTO.getId()));
    }

    //상품 추가
    @PostMapping
    public ResponseEntity<Void> addWishList(@LoginMember MemberDTO memberDTO,
        @RequestBody WishListRequest wishListRequest) {
        wishListService.addProduct(memberDTO.getId(), wishListRequest.getProductId());
        return ResponseEntity.ok().build();
    }

    //상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteWishList(@LoginMember MemberDTO memberDTO,
        @RequestBody WishListRequest wishListRequest) {
        wishListService.deleteProduct(memberDTO.getId(), wishListRequest.getProductId());
        return ResponseEntity.ok().build();
    }

    //상품 수정
    @PutMapping
    public ResponseEntity<Void> updateWishList(@LoginMember MemberDTO memberDTO,
        @RequestBody WishListRequest wishListRequest) {
        wishListService.updateProduct(memberDTO.getId(), wishListRequest.getProductId(),
            wishListRequest.getProductCount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{page}")
    public ResponseEntity<WishListDTO> getWishListPage(@LoginMember MemberDTO memberDTO,
        @PathVariable int page) {
        return ResponseEntity.ok(wishListService.getWishListPage(memberDTO.getId(), page, 10));
    }


}
