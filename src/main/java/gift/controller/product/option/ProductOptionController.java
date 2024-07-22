package gift.controller.product.option;

import gift.domain.product.option.ProductOption;
import gift.service.product.option.ProductOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class ProductOptionController {

    @Autowired
    private ProductOptionService productOptionService;

    private static final Logger logger = LoggerFactory.getLogger(ProductOptionController.class);

    @PostMapping("/{productId}")
    public ResponseEntity<ProductOption> addProductOption(
            @PathVariable Long productId,
            @RequestBody ProductOptionRequest request) {
        logger.info("Received addProductOption request for productId {}", productId);
        ProductOption option = productOptionService.addProductOption(productId, request.getName(), request.getQuantity());
        logger.info("Added product option: {}", option);
        return ResponseEntity.ok(option);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductOption>> getProductOptions(@PathVariable Long productId) {
        logger.info("Received getProductOptions request for productId {}", productId);
        List<ProductOption> options = productOptionService.getProductOptions(productId);
        logger.info("Retrieved {} product options", options.size());
        return ResponseEntity.ok(options);
    }

    @PostMapping("/{productId}/subtract")
    public ResponseEntity<Void> subtractProductOptionQuantity(
            @PathVariable Long productId,
            @RequestBody SubtractProductOptionQuantityRequest request) {
        logger.info("Received subtractProductOptionQuantity request for productId {} and option {}", productId, request.getOptionName());
        productOptionService.subtractProductOptionQuantity(productId, request.getOptionName(), request.getQuantity());
        logger.info("Subtracted {} from option {} for productId {}", request.getQuantity(), request.getOptionName(), productId);
        return ResponseEntity.ok().build();
    }

    static class ProductOptionRequest {
        private String name;
        private Long quantity;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Long getQuantity() { return quantity; }
        public void setQuantity(Long quantity) { this.quantity = quantity; }
    }

    static class SubtractProductOptionQuantityRequest {
        private String optionName;
        private Long quantity;

        public String getOptionName() { return optionName; }
        public void setOptionName(String optionName) { this.optionName = optionName; }
        public Long getQuantity() { return quantity; }
        public void setQuantity(Long quantity) { this.quantity = quantity; }
    }
}
