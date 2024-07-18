package gift.controller;

import gift.dto.OptionRequest;
import gift.model.Option;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
public class OptionController {
    private final OptionService optionService;
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }
    @GetMapping("/{productId}/options")
    public ResponseEntity<List<Option>> getOptionsByProductId(@PathVariable Long productId) {
        List<Option> options = optionService.getOptionByProductId(productId);
        return ResponseEntity.ok().body(options);
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<Void> makeOption(@PathVariable Long productId, @RequestBody @Valid OptionRequest request) {
       optionService.makeOption(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
