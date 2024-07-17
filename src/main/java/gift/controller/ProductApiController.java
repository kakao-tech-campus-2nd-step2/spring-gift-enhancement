package gift.controller;

import gift.domain.model.dto.ProductAddRequestDto;
import gift.domain.model.dto.ProductResponseDto;
import gift.domain.model.dto.ProductUpdateRequestDto;
import gift.domain.model.enums.ProductSortBy;
import gift.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    //    id로 상품 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    //    전체 상품 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDto> getAllProducts(
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.") int page,
        @RequestParam(defaultValue = "ID_DESC") ProductSortBy sortBy) {
        return productService.getAllProducts(page, sortBy);
    }

    //    상품 추가
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto addProduct(
        @Valid @RequestBody ProductAddRequestDto productAddRequestDto) {
        return productService.addProduct(productAddRequestDto);
    }

    //    상품 수정
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto updateProduct(
        @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        return productService.updateProduct(productUpdateRequestDto);
    }

    //    상품 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
