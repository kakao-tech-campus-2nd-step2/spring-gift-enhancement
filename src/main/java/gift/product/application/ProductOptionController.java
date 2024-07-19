package gift.product.application;

import gift.product.application.dto.request.ProductOptionRequest;
import gift.product.application.dto.response.ProductOptionResponse;
import gift.product.service.ProductOptionService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class ProductOptionController {
    private final ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @PostMapping
    public ResponseEntity<Void> createProductOption(@PathVariable Long productId,
                                                    @Valid @RequestBody ProductOptionRequest request
    ) {
        var optionId = productOptionService.createProductOption(productId, request.toProductOptionCommand());

        return ResponseEntity.created(URI.create("/api/products/" + productId + "/options/" + optionId))
                .build();
    }

    @PatchMapping("/{optionId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductOption(@PathVariable Long productId,
                                    @PathVariable Long optionId,
                                    @Valid @RequestBody ProductOptionRequest request
    ) {
        productOptionService.modifyProductOption(productId, optionId, request.toProductOptionCommand());
    }

    @GetMapping("/{optionId}")
    public ResponseEntity<ProductOptionResponse> getProductOption(@PathVariable Long productId,
                                                                  @PathVariable Long optionId
    ) {
        var productOption = productOptionService.getProductOptionInfo(productId, optionId);

        var response = ProductOptionResponse.from(productOption);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponse>> getAllProductOptions(@PathVariable Long productId) {
        var productOptions = productOptionService.getAllProductOptions(productId);

        var response = productOptions.stream()
                .map(ProductOptionResponse::from)
                .toList();
        return ResponseEntity.ok()
                .body(response);
    }
}
