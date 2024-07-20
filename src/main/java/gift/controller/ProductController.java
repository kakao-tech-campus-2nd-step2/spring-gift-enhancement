package gift.controller;

import gift.dto.OptionDto;
import gift.dto.ProductDto;
import gift.dto.request.OptionCreateRequest;
import gift.dto.request.ProductCreateRequest;
import gift.dto.request.ProductUpdateRequest;
import gift.dto.response.OptionResponse;
import gift.dto.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> productList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ProductDto> productDtoList = productService.getProducts(pageable);

        List<ProductResponse> productResponseList = productDtoList.stream()
                .map(ProductDto::toResponseDto)
                .toList();

        PageImpl<ProductResponse> response = new PageImpl<>(productResponseList, pageable, productResponseList.size());

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> productOne(@PathVariable Long productId) {
        ProductDto productDto = productService.getProduct(productId);

        ProductResponse response = productDto.toResponseDto();

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Void> productAdd(@RequestBody @Valid ProductCreateRequest request) {
        List<OptionDto> optionDtoList = request.getOptions().stream()
                .map(OptionCreateRequest::toDto)
                .toList();
        ProductDto productDto = new ProductDto(request.getName(), request.getPrice(), request.getImageUrl(), request.getCategoryId(), optionDtoList);

        productService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> productEdit(@PathVariable Long productId,
                                            @RequestBody @Valid ProductUpdateRequest request) {
        ProductDto productDto = new ProductDto(request.getName(), request.getPrice(), request.getImageUrl(), request.getCategoryId());

        productService.editProduct(productId, productDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> productRemove(@PathVariable Long productId) {
        productService.removeProduct(productId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}/options")
    public ResponseEntity<List<OptionResponse>> optionList(@PathVariable Long productId) {
        List<gift.dto.OptionDto> options = productService.getOptions(productId);

        List<OptionResponse> response = options.stream()
                .map(gift.dto.OptionDto::toResponseDto)
                .toList();

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<Void> optionAdd(@PathVariable Long productId, @RequestBody @Valid OptionCreateRequest request) {
        gift.dto.OptionDto optionDto = new gift.dto.OptionDto(request.getName(), request.getQuantity());

        productService.addOption(productId, optionDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<Void> optionRemove(@PathVariable Long productId, @PathVariable Long optionId) {
        productService.removeOption(productId, optionId);

        return ResponseEntity.ok().build();
    }

}
