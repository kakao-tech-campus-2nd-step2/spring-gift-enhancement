package gift.controller;

import gift.model.Option;
import gift.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class OptionApiController {

    private final OptionService optionService;

    public OptionApiController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{productId}/options")
    public ResponseEntity<List<Option>> getOptionsByProductId(@PathVariable Long productId) {
        List<Option> options = optionService.getOptionsByProductId(productId);
        return ResponseEntity.ok(options);
    }
}
