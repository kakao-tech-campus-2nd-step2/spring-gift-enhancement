package gift.controller;

import gift.domain.ProductOption.optionDetail;
import gift.domain.ProductOrder.decreaseProductOption;
import gift.service.ProductOrderService;
import gift.util.page.SingleResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/{productId}/order")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/{optionId}")
    public SingleResult<optionDetail> decreaseProductOption(@PathVariable Long productId,
        @PathVariable Long optionId, @Valid @RequestBody decreaseProductOption decrease) {
        return new SingleResult<>(
            productOrderService.decreaseProductOption(productId, optionId, decrease));
    }
}
