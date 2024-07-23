package gift.controller;

import gift.dto.OptionDTO;
import gift.service.OptionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<OptionDTO>> getOptions(@PathVariable("product_id") long productId) {
        return ResponseEntity.ok().body(optionService.getOptions(productId));
    }

    @PostMapping
    public ResponseEntity<OptionDTO> addOption(@PathVariable("product_id") long productId, @Valid @RequestBody OptionDTO optionDTO) {
        return ResponseEntity.ok().body(optionService.addOption(productId, optionDTO));
    }

    @PutMapping
    public ResponseEntity<OptionDTO> updateOption(@PathVariable("product_id") long productId, @Valid @RequestBody OptionDTO optionDTO) {
        return ResponseEntity.ok().body(optionService.updateOption(productId, optionDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OptionDTO> deleteOption(@PathVariable("product_id") long productId, @PathVariable("id") long id) {
        return ResponseEntity.ok().body(optionService.deleteOption(productId, id));
    }
}
