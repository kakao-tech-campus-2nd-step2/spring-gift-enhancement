package gift.product.controller;

import gift.global.annotation.CategoryInfo;
import gift.global.annotation.PageInfo;
import gift.global.dto.ApiResponseDto;
import gift.global.dto.CategoryInfoDto;
import gift.global.dto.PageInfoDto;
import gift.product.dto.ProductRequestDto;
import gift.product.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // 제품을 추가하는 핸들러
    @PostMapping("/admin/products")
    @Parameter(name = "category-id", required = true)
    public ApiResponseDto createProduct(
        @RequestBody @Valid ProductRequestDto productRequestDto,
        @CategoryInfo CategoryInfoDto categoryInfoDto) {
        System.out.println(categoryInfoDto.categoryId());
        productService.insertProduct(productRequestDto, categoryInfoDto);

        return ApiResponseDto.of();
    }

    // 페이지 내의 제품을 조회하는 핸들러.
    @Parameters({
        @Parameter(name = "page-no"),
        @Parameter(name = "page-size"),
        @Parameter(name = "sort-property"),
        @Parameter(name = "sort-direction")
    })
    @GetMapping("/users/products")
    public ApiResponseDto readUserProducts(@PageInfo PageInfoDto pageInfoDto) {
        return ApiResponseDto.of(productService.selectProducts(pageInfoDto));
    }

    // id가 i인 상품을 수정하는 핸들러
    @PutMapping("/admin/products/{product-id}")
    @Parameter(name = "category-id", required = true)
    public ApiResponseDto updateProduct(@PathVariable(name = "product-id") long productId,
        @RequestBody @Valid ProductRequestDto productRequestDto,
        @CategoryInfo CategoryInfoDto categoryInfoDto) {
        System.out.println(productRequestDto.name());
        // service를 호출해서 제품 수정
        productService.updateProduct(productId, productRequestDto, categoryInfoDto);

        return ApiResponseDto.of();
    }

    // id가 i인 상품을 삭제하는 핸들러
    @DeleteMapping("/admin/products/{product-id}")
    public ApiResponseDto deleteProduct(@PathVariable(name = "product-id") long productId) {
        // service를 사용해서 하나의 제품 제거
        productService.deleteProduct(productId);

        return ApiResponseDto.of();
    }
}
