package gift.product.restapi;

import gift.core.domain.product.ProductOptionService;
import gift.product.restapi.dto.request.ProductOptionRegisterRequest;
import gift.product.restapi.dto.response.ProductOptionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductOptionController {
    private final ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @GetMapping("/{productId}/options")
    public List<ProductOptionResponse> getOptions(
            @PathVariable("productId") Long productId
    ) {
        return productOptionService
                .getOptionsFromProduct(productId)
                .stream()
                .map(ProductOptionResponse::from)
                .toList();
    }

    @PostMapping("/{productId}/options")
    public void registerOption(
            @PathVariable Long productId,
            @RequestBody ProductOptionRegisterRequest request
    ) {
        productOptionService.registerOptionToProduct(productId, request.toDomain());
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public void deleteOption(
            @PathVariable Long productId,
            @PathVariable Long optionId
    ) {
        productOptionService.removeOptionFromProduct(productId, optionId);
    }
}
