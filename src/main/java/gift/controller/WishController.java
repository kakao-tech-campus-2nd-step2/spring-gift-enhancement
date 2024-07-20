package gift.controller;

import gift.config.LoginMember;
import gift.domain.member.Member;
import gift.dto.ProductDto;
import gift.dto.request.WishCreateRequest;
import gift.dto.response.ProductResponse;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/wishes")
@RestController
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> productList(@LoginMember Member member,
                                                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ProductDto> productDtoList = wishService.getProducts(member, pageable);

        List<ProductResponse> productResponseList = productDtoList.stream()
                .map(ProductDto::toResponseDto)
                .toList();

        PageImpl<ProductResponse> response = new PageImpl<>(productResponseList, pageable, productResponseList.size());

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Void> productAdd(@LoginMember Member member,
                                           @RequestBody WishCreateRequest request) {
        wishService.addProduct(member, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> productRemove(@LoginMember Member member,
                                              @RequestBody WishCreateRequest request) {
        wishService.removeProduct(member, request.getProductId());

        return ResponseEntity.ok()
                .build();
    }

}
