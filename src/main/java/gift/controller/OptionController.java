package gift.controller;

import gift.domain.Category;
import gift.domain.Option;
import gift.service.CategoryService;
import gift.service.OptionService;
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
@RequestMapping("/api/products/{product_id}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOptions(@PathVariable("product_id") Long productId) {
        return ResponseEntity.ok(optionService.getAllOptionByProductId(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOptionById(
        @PathVariable("product_id") Long productId,
        @PathVariable("id") Long id) {
        return ResponseEntity.ok(optionService.getOptionByProductIdAndId(productId,id));
    }

    @PostMapping
    public ResponseEntity<?> addOption(
        @PathVariable("product_id") Long productId,
        @Valid @RequestBody Option option) {
        optionService.addOption(productId, option);
        return ResponseEntity.status(HttpStatus.CREATED).body("Option added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOption(
        @PathVariable("id") Long id,
        @Valid @RequestBody Option option) {
        optionService.updateOption(id, option);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable("id") Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.ok().build();
    }

}
