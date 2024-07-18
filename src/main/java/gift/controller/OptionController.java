package gift.controller;

import gift.dto.OptionDto;
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

    @PostMapping
    public ResponseEntity<Void> addNewOption(@PathVariable Long productId, @RequestBody OptionDto optionDto) {
        optionService.addNewOption(productId, optionDto);
        return ResponseEntity.status(201).build();
    }
}
