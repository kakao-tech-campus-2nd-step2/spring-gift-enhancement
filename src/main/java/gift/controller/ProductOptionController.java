package gift.controller;

import gift.model.ProductOption;
import gift.service.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductOptionController {

    @Autowired
    private ProductOptionService productOptionService;

    @GetMapping("/{productId}/options")
    public ResponseEntity<List<ProductOption>> getProductOptions(@PathVariable Long productId) {
        List<ProductOption> options = productOptionService.getOptionsByProductId(productId);
        return ResponseEntity.ok(options);
    }
}