package gift.product.controller;

import gift.product.dto.OptionDto;
import gift.product.dto.OptionResponse;
import gift.product.model.Option;
import gift.product.service.OptionService;
import java.util.List;
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
@RequestMapping("/api")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/options")
    public ResponseEntity<List<Option>> getOptionAll() {
        return ResponseEntity.ok(optionService.getOptionAll());
    }

    @GetMapping("/options/{id}")
    public ResponseEntity<Option> getOption(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(optionService.getOption(id));
    }

    @GetMapping("/products/{id}/options")
    public ResponseEntity<List<OptionResponse>> getOptionAllByProductId(@PathVariable(name = "id") Long productId) {
        return ResponseEntity.ok(optionService.getOptionAllByProductId(productId));
    }

    @PostMapping("/options/insert")
    public ResponseEntity<Option> insertOption(@RequestBody OptionDto optionDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(optionService.insertOption(optionDto));
    }

    @PutMapping("/options/update/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable(name = "id") Long id, @RequestBody OptionDto optionDto) {
        return ResponseEntity.ok(optionService.updateOption(id, optionDto));
    }

    @DeleteMapping("/options/delete/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable(name = "id") Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.ok().build();
    }

}
