package gift.controller;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class ProductOptionController {

    private final ProductOptionService optionService;

    public ProductOptionController(ProductOptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addOption(@PathVariable Long productId, @Valid @RequestBody OptionRequest optionRequest) {
        var option = optionService.addOption(productId, optionRequest);
        return ResponseEntity.created(URI.create("/api/products/" + productId + "/options/" + option.id())).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateOption(@PathVariable Long productId, @PathVariable Long id, @Valid @RequestBody OptionRequest optionRequest) {
        optionService.updateOption(productId, id, optionRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable Long productId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var options = optionService.getOptions(productId, pageable);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long productId, @PathVariable Long id) {
        optionService.deleteOption(productId, id);
        return ResponseEntity.noContent().build();
    }
}
