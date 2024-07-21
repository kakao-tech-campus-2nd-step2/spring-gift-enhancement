package gift.wishlist.controller;

import gift.global.annotation.PageInfo;
import gift.global.annotation.ProductInfo;
import gift.global.annotation.UserInfo;
import gift.global.dto.ApiResponseDto;
import gift.global.dto.PageInfoDto;
import gift.global.dto.ProductInfoDto;
import gift.global.dto.UserInfoDto;
import gift.wishlist.dto.WishListIdDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 개인의 wish list db를 조작해서 결과를 가져오는 api 컨트롤러

@RestController
@RequestMapping("/api/users/")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class WishListApiController {

    private final WishListService wishListService;

    @Autowired
    public WishListApiController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 전체 목록에서 제품 추가 시
    @Parameter(name = "product-id", required = true)
    @PostMapping("/wishlist")
    public ApiResponseDto addWishProduct(@ProductInfo ProductInfoDto productInfoDto,
        @UserInfo UserInfoDto userInfoDto) {
        wishListService.insertWishProduct(productInfoDto, userInfoDto);
        return ApiResponseDto.of();
    }

    // 나의 위시 페이지 가져오기
    @GetMapping("/wishlist")
    @Parameters({
        @Parameter(name = "page-no"),
        @Parameter(name = "page-size"),
        @Parameter(name = "sort-property"),
        @Parameter(name = "sort-direction")
    })
    public List<WishListResponseDto> getWishProducts(@UserInfo UserInfoDto userInfoDto, @PageInfo
    PageInfoDto pageInfoDto) {
        return wishListService.readWishProducts(userInfoDto, pageInfoDto);
    }

    // + 버튼 눌러서 하나 증가.
    @PutMapping("/wishlist/{wishlist-id}/increase")
    public ApiResponseDto increaseWishProduct(@PathVariable(name = "wishlist-id") long wishListId) {
        wishListService.increaseWishProduct(new WishListIdDto(wishListId));
        return ApiResponseDto.of();
    }

    // - 버튼 눌러서 하나 감소
    @PutMapping("/wishlist/{wishlist-id}/decrease")
    public ApiResponseDto decreaseWishProduct(@PathVariable(name = "wishlist-id") Long wishListId) {
        wishListService.decreaseWishProduct(new WishListIdDto(wishListId));
        return ApiResponseDto.of();
    }

    // 삭제 버튼 눌러서 삭제
    @DeleteMapping("/wishlist/{wishlist-id}")
    public ApiResponseDto deleteWishProduct(@PathVariable(name = "wishlist-id") Long wishListId,
        @UserInfo UserInfoDto userInfoDto) {
        wishListService.deleteWishProduct(new WishListIdDto(wishListId), userInfoDto);
        return ApiResponseDto.of();
    }
}
