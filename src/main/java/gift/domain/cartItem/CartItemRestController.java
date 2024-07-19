package gift.domain.cartItem;

import gift.domain.cartItem.dto.CartItemDTO;
import gift.domain.product.Product;
import gift.domain.user.dto.UserInfo;
import gift.global.resolver.LoginInfo;
import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/cart")
public class CartItemRestController {

    private final CartItemService cartItemService;

    public CartItemRestController(CartItemService cartItemService, JdbcTemplate jdbcTemplate) {
        this.cartItemService = cartItemService;
    }


    /**
     * 장바구니에 상품 담기
     */
    @PostMapping("/{productId}")
    public ResponseEntity<ResultResponseDto<Integer>> addCartItem(
        @PathVariable("productId") Long productId, @LoginInfo UserInfo userInfo) {

        int currentCount = cartItemService.addCartItem(userInfo.getId(), productId);

        return ResponseMaker.createResponse(HttpStatus.OK,
            "상품이 장바구니에 추가되었습니다. 총 개수: " + currentCount, currentCount);
    }

    /**
     * 장바구니 조회 - 페이징(매개변수별)
     */
    @GetMapping
    public ResponseEntity<ResultResponseDto<List<CartItemDTO>>> getProductsInCartByUserIdAndPageAndSort(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sort", defaultValue = "id_asc") String sort,
        @LoginInfo UserInfo userInfo) {
        int size = 10; // default
        Sort sortObj = getSortObject(sort);

        List<CartItemDTO> cartItemDTOS = cartItemService.getProductsInCartByUserIdAndPageAndSort(
            userInfo.getId(),
            page,
            size,
            sortObj
        );

        return ResponseMaker.createResponse(HttpStatus.OK, "장바구니 조회에 성공했습니다.", cartItemDTOS);
    }

    /**
     * 장바구니 상품 삭제
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<SimpleResultResponseDto> deleteCartItem(
        @PathVariable("cartItemId") Long cartItemId, @LoginInfo UserInfo userInfo) {

        cartItemService.deleteCartItem(cartItemId);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "장바구니에서 상품이 삭제되었습니다.");
    }

    /**
     * 장바구니 상품 수량 변경
     */
    // TODO cartItem 에 userId, productId, + 상품 정보까지 담는걸로
    // 안그러면 productId 로 다시 상품 정보를 불러와야 함..페이징할때도 cartItem 에서 바로 할 수 있으니 나을 것 같다
    @PutMapping("/{cartItemId}")
    public ResponseEntity<SimpleResultResponseDto> updateCartItem(
        @PathVariable("cartItemId") Long cartItemId, 
        @RequestParam("count") int count,
        @LoginInfo UserInfo userInfo
    ) {
        int modifiedCount = cartItemService.updateCartItem(cartItemId, count);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK,
            "해당 상품의 수량이 변경되었습니다. 총 개수: " + modifiedCount + "개");
    }

    // TODO 페이징 기준을 cartItem 에 맞춰서 수정해야함.. 간단한건 생성날짜? <- 추가 속성 필요
    private Sort getSortObject(String sort) {
        switch (sort) {
            case "price_asc":
                return Sort.by(Sort.Direction.ASC, "price");
            case "price_desc":
                return Sort.by(Sort.Direction.DESC, "price");
            case "name_asc":
                return Sort.by(Sort.Direction.ASC, "name");
            case "name_desc":
                return Sort.by(Sort.Direction.DESC, "name");
            default:
                return Sort.by(Sort.Direction.ASC, "id");
        }
    }
}
