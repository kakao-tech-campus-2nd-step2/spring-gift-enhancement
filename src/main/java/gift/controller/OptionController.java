package gift.controller;

import gift.model.Option;
import gift.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{productId}/options")
    public ResponseEntity<List<Option>> getOptionsByProductId(@PathVariable Long productId) {
        List<Option> options = optionService.getOptionsByProductId(productId);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<Option> addOption(@PathVariable Long productId, @RequestBody Option option) {
        Option newOption = optionService.addOption(productId, option);
        return ResponseEntity.status(201).body(newOption);
    }

    @PutMapping("/{productId}/options/{optionId}")
    public ResponseEntity<Option> updateOption(@PathVariable Long optionId, @RequestBody Option optionDetails) {
        Option updatedOption = optionService.updateOption(optionId, optionDetails);
        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }
}
