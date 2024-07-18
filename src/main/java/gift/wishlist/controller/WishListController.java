package gift.wishlist.controller;

import gift.product.model.Product;
import gift.wishlist.service.WishListService;
import gift.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 회원 id로 위시리스트 연결하려면
    @GetMapping("/{memberId}")
    public WishList getWishList(@PathVariable Long memberId) {
        return wishListService.findByMemberId(memberId);
    }

    /** 페이지네이션된 위시리스트 데이터를 반환
     * 특정 페이지와 크기 요청: /wishlist/{member_id}/paged?page=1&size=5
     * 페이지 번호: 0
     * 페이지 크기: 10
     * 정렬: 이름을 기준으로 오름차순 정렬 (기본값) **/
    @GetMapping("/{memberId}/paged")
    public Page<WishList> getWishListPaged(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") int page, // 요청 파라미터 page를 받아 페이지 번호를 설정
            @RequestParam(defaultValue = "10") int size, // 요청 파라미터 size를 받아 페이지 크기를 설정
            @RequestParam(defaultValue = "name,asc") String[] sort // 요청 파라미터 sort를 받아 정렬 기준과 방향을 설정
    ) {
        int maxSize = 50; // 최대 페이지 크기를 50으로 제한
        size = Math.min(size, maxSize); // 50 초과 입력시 50으로 설정

        String sortBy = sort[0]; // sort 배열의 첫 번째 요소는 정렬 기준
        Sort.Direction direction = Sort.Direction.fromString(sort[1]); // sort 배열의 두 번째 요소는 정렬 방향

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy)); // Pageable 객체를 생성

        return wishListService.findByMemberId(memberId, pageable); // 페이지네이션된 위시리스트 데이터를 반환
    }

    // 해당 위시리스트에 상품 추가 연결
    @PostMapping("/{memberId}/add")
    public void addProductToWishList(@PathVariable Long memberId, @RequestBody Product product) {
        wishListService.addProductToWishList(memberId, product);
    }

    // 해당 위시리스트에 상품 삭제 연결
    @DeleteMapping("/{memberId}/remove/{productId}")
    public void removeProductFromWishList(@PathVariable Long memberId, @PathVariable Long productId) {
        wishListService.removeProductFromWishList(memberId, productId);
    }
}