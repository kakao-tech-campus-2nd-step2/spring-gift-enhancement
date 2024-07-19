package gift.product.controller;

import gift.product.model.dto.option.OptionResponse;
import gift.product.service.OptionService;
import gift.product.service.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class OptionController {
    private final OptionService optionService;
    private final ProductService productService;

    public OptionController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponse>> findOptionsByProductId(@PathVariable Long id) {
        List<OptionResponse> options = optionService.findOptionsByProductId(id);
        return ResponseEntity.ok().body(options);
    }
}
