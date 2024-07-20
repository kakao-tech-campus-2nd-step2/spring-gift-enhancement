package gift.controller;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.service.OptionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable Long productId) {
        List<OptionResponse> options = optionService.getOptions(productId);
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<OptionResponse> addOption(@PathVariable Long productId,
        @Validated @RequestBody OptionRequest optionRequest) {
        OptionResponse optionResponse = optionService.addOption(productId, optionRequest);
        return ResponseEntity.status(201).body(optionResponse);
    }

    @DeleteMapping("/{optionId}")
    public void deleteOption(@PathVariable Long optionId, @PathVariable Long productId) {
        optionService.deleteOption(optionId, productId);
    }

}
