package gift.controller.wish;

import gift.dto.gift.GiftResponse;
import gift.dto.paging.PagingRequest;
import gift.dto.paging.PagingResponse;
import gift.dto.wish.WishResponse;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.service.gift.GiftService;
import gift.service.wish.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WishListController {

    private final WishService wishService;
    private final GiftService giftService;

    @Autowired
    public WishListController(WishService wishService, GiftService giftService) {
        this.wishService = wishService;
        this.giftService = giftService;
    }

    @GetMapping("/wish")
    public ResponseEntity<?> getGiftList(@ModelAttribute PagingRequest pagingRequest) {
        PagingResponse<GiftResponse> gifts = giftService.getAllGifts(pagingRequest.getPage(), pagingRequest.getSize());
        return ResponseEntity.ok(gifts);
    }

    @PostMapping("/wish/{giftId}")
    public ResponseEntity<String> addGiftToCart(@RequestAttribute("user") User user,
                                                @PathVariable Long giftId,
                                                @RequestParam(required = false, defaultValue = "1") int quantity) {
        wishService.addGiftToUser(user.getId(), giftId, quantity);
        return ResponseEntity.ok("위시리스트에 상품이 추가되었습니다.");

    }

    @PutMapping("/wish/{giftId}")
    public ResponseEntity<String> updateGiftQuantity(@RequestAttribute("user") User user,
                                                     @PathVariable Long giftId,
                                                     @RequestParam(name = "quantity") int quantity) {
        wishService.updateWishQuantity(user.getId(), giftId, quantity);
        return ResponseEntity.ok("카트에서 상품수량이 변경되었습니다.");

    }

    @DeleteMapping("/wish/{giftId}")
    public ResponseEntity<String> removeGiftFromCart(@RequestAttribute("user") User user,
                                                     @PathVariable Long giftId) {
        wishService.removeGiftFromUser(user.getId(), giftId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("카트에서 상품이 삭제되었습니다.");

    }

    @GetMapping("/mywish")
    public ResponseEntity<PagingResponse<WishResponse>> getUserGifts(@RequestAttribute("user") User user,
                                                                     @ModelAttribute PagingRequest pagingRequest) {
        PagingResponse<Wish> userWishes = wishService.getGiftsForUser(user.getId(), pagingRequest.getPage(), pagingRequest.getSize());
        List<WishResponse> wishResponses =
                userWishes.getContent()
                        .stream()
                        .map(wish -> new WishResponse(wish.getGift().getId(), wish.getGift().getName(), wish.getGift().getPrice(), wish.getQuantity())).collect(Collectors.toList());
        PagingResponse<WishResponse> pagingResponse = new PagingResponse<>(pagingRequest.getPage(), wishResponses, pagingRequest.getSize(), userWishes.getTotalElements(), userWishes.getTotalPages());
        return ResponseEntity.ok(pagingResponse);

    }
}
