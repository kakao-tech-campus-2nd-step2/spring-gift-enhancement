package gift.controller.restcontroller;

import gift.controller.dto.request.CreateProductRequest;
import gift.controller.dto.request.UpdateProductRequest;
import gift.controller.dto.response.OptionResponse;
import gift.controller.dto.response.PagingResponse;
import gift.controller.dto.response.ProductResponse;
import gift.service.OptionService;
import gift.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
    private final ProductService productService;
    private final OptionService optionService;

    public ProductRestController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    @GetMapping("/products")
    @Operation(summary = "전체 상품 조회", description = "전체 상품을 조회합니다.")
    public ResponseEntity<PagingResponse<ProductResponse>> getProducts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PagingResponse<ProductResponse> responses = productService.findAllProductPaging(pageable);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "상품 조회", description = "특정 상품을 조회합니다.")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        ProductResponse response = productService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/product")
    @Operation(summary = "상품 추가", description = "상품을 추가합니다.")
    public ResponseEntity<Long> createProduct(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CreateProductRequest request
    ) {
        Long id = productService.save(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriBuilder.path("/api/v1/product/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(id);
    }

    @PutMapping("/product")
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    public ResponseEntity<Void> updateProduct(
            @Valid @RequestBody UpdateProductRequest request
    ) {
        productService.updateById(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}/options")
    @Operation(summary = "상품 옵션 조회", description = "상품의 옵션을 조회합니다.")
    public ResponseEntity<List<OptionResponse>> getOptions(
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        List<OptionResponse> options = optionService.getAllOptions(id);
        return ResponseEntity.ok().body(options);
    }
}
