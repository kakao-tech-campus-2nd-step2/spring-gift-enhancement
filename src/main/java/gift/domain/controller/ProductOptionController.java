package gift.domain.controller;

import gift.domain.controller.apiResponse.ProductOptionsGetApiResponse;
import gift.domain.dto.request.OptionRequest;
import gift.domain.service.ProductService;
import gift.global.apiResponse.BasicApiResponse;
import gift.global.apiResponse.SuccessApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductOptionController {

    private final ProductService productService;

    public ProductOptionController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/options")
    public ResponseEntity<ProductOptionsGetApiResponse> getProductOptions(@PathVariable("productId") Long productId) {
        return SuccessApiResponse.ok(new ProductOptionsGetApiResponse(HttpStatus.OK, productService.getOptionsByProductId(productId)));
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<BasicApiResponse> addProductOption(@PathVariable("productId") Long productId, @Valid @RequestBody OptionRequest optionRequest) {
        productService.addProductOption(productId, optionRequest);
        return SuccessApiResponse.ok();
    }

    @PutMapping("/{productId}/options/{optionId}")
    public ResponseEntity<BasicApiResponse> updateProduct(@PathVariable("productId") Long productId,
                                                          @PathVariable("optionId") Long optionId,
                                                          @Valid @RequestBody OptionRequest optionRequest
    ) {
        productService.updateProductOptionById(productId, optionId, optionRequest);
        return SuccessApiResponse.ok();
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<BasicApiResponse> deleteProduct(@PathVariable("productId") Long productId, @PathVariable("optionId") Long optionId) {
        productService.deleteProductOption(productId, optionId);
        return SuccessApiResponse.ok();
    }
}
