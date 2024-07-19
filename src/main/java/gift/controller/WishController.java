package gift.controller;

import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.ProductOption;
import gift.model.Wish;
import gift.service.ProductService;
import gift.service.ProductOptionService;
import gift.service.WishService;
import gift.annotation.LoginMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionService productOptionService;

    @GetMapping
    public Page<WishResponse> getWishes(@LoginMember Member member, Pageable pageable) {
        return wishService.getWishesByMemberId(member.getId(), pageable);
    }

    @PostMapping
    public Wish addWish(@RequestBody WishRequest wishRequest, @LoginMember Member member) {
        Product product = productService.findById(wishRequest.getProductId());
        ProductOption productOption = productOptionService.findProductOptionById(wishRequest.getOptionId());
        if (productOption == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product option");
        }
        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wish.setProductOption(productOption);
        return wishService.addWish(wish);
    }

    @DeleteMapping("/{wishId}")
    public void deleteWish(@PathVariable Long wishId, @LoginMember Member member) {
        wishService.deleteWish(wishId);
    }
}
