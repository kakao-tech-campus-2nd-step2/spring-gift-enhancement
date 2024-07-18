package gift.user.controller;

import gift.global.annotation.PageInfo;
import gift.global.annotation.UserInfo;
import gift.global.dto.PageInfoDto;
import gift.global.dto.UserInfoDto;
import gift.product.dto.ProductResponseDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

// login한 user에게 보여줄 view를 반환하는 Controller
@Controller
@Tag(name = "user-controller", description = "view 반환")
@RequestMapping("/users")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

    private final WishListService wishListService;

    @Autowired
    public UserController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 로그인 직후에 메인 화면(wishlist)을 보여주는 핸들러
    @GetMapping("/wishlist")
    @Operation
    public String loadUserWishList(@UserInfo UserInfoDto userInfoDto,
        @PageInfo PageInfoDto pageInfoDto, Model model) {
        // 특정 id를 갖는 사람이 추가한 위시 리스트 페이지를 가져와서 thymeleaf를 통해 html로 전송
        List<WishListResponseDto> wishListResponseDtoList = wishListService.readWishProducts(
            userInfoDto, pageInfoDto);

        model.addAttribute("products", wishListResponseDtoList);
        model.addAttribute("userInfo", userInfoDto);
        model.addAttribute("pageInfo", pageInfoDto);

        return "html/user-main";
    }

    // 모든 제품을 추가하는 화면을 보여주는 핸들러
    // 모든 제품을 가져오는 행위는 resolver로 처리가 가능해서 resolver를 사용하였습니다.
    @GetMapping("/products")
    @Operation
    public String loadAddingPage(@PathVariable(name = "user-id") long userId,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
        List<ProductResponseDto> products, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("products", products);
        model.addAttribute("token", token);
        return "html/user-all-products";
    }
}
