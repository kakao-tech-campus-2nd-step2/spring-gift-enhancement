package gift.controller;

import gift.domain.Member;
import gift.dto.ProductDTO;
import gift.dto.WishDTO;
import gift.service.ProductService;
import gift.service.WishService;
import gift.util.LoginMember;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;
    private final ProductService productService;

    public WishController(WishService wishService, ProductService productService) {
        this.wishService = wishService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<WishDTO>> getWishes(@LoginMember Member member) {
        List<WishDTO> wishes = wishService.getWishesByMemberId(member.getId()).stream()
            .map(WishDTO::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(wishes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WishDTO>> getPagedWishes(@LoginMember Member member, Pageable pageable) {
        Page<WishDTO> wishes = wishService.getWishesByMemberId(member.getId(), pageable)
            .map(WishDTO::from);
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<String> addWish(@RequestBody @Valid WishDTO wishDTO, @LoginMember Member member) {
        ProductDTO productDTO = productService.getProductById(wishDTO.getProductId());
        wishService.addWish(wishDTO.toEntity(member, productDTO.toEntity(null)));
        return ResponseEntity.ok("Wish added successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> removeWish(@RequestBody @Valid WishDTO wishDTO, @LoginMember Member member) {
        wishService.removeWish(member.getId(), wishDTO.getProductId());
        return ResponseEntity.ok("Wish removed successfully");
    }
}
