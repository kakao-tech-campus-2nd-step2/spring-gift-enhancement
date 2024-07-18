package gift.controller;

import gift.model.product.Option;
import gift.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequestMapping("/api/products/{productId}/options")
@Controller
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<Option>> getAllOptionsById(@PathVariable Long productId) {
        List<Option> options = optionService.getAllOptionsById(productId);
        return ResponseEntity.ok(options);
    }
}
