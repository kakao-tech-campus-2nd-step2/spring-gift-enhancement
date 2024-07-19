package gift.product.option.controller;

import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.dto.request.UpdateOptionRequest;
import gift.product.option.dto.response.OptionResponse;
import gift.product.option.service.OptionService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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
    public ResponseEntity<List<OptionResponse>> getOptions(
        @PathVariable("product_id") Long productId) {
        return ResponseEntity.ok(optionService.getProductOptions(productId));
    }

    @PostMapping
    public ResponseEntity<Long> addOption(@PathVariable("product_id") Long productId,
        @RequestBody @Valid CreateOptionRequest request) {
        Long id = optionService.createOption(request);
        URI location = URI.create("/api/products/" + productId + "/options" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOption(@PathVariable("product_id") Long productId,
        @PathVariable("id") Long id, @RequestBody @Valid UpdateOptionRequest request) {
        optionService.updateOption(productId, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable("product_id") Long productId,
        @PathVariable("id") Long id) {
        optionService.deleteOption(productId, id);
        return ResponseEntity.ok().build();
    }
}
