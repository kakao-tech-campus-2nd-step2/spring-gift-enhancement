package gift.option.controller;

import gift.option.domain.Option;
import gift.option.domain.OptionRequest;
import gift.option.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option")
public class OptionController {
    private final OptionService optionService;
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    // 1. option create
    @PostMapping
    public ResponseEntity<Option> createOption(@Valid @RequestBody OptionRequest optionRequest) {
        Option option = optionService.createOption(optionRequest);
        return ResponseEntity.ok(option);
    }

    // 2. option read
    @GetMapping("/{id}")
    public ResponseEntity<Option> getOption(@PathVariable Long id) {
        Option option = optionService.getOption(id);
        return ResponseEntity.ok(option);
    }

    @GetMapping
    public ResponseEntity<List<Option>> getAllOptions() {
        List<Option> options = optionService.getAllOptions();
        return ResponseEntity.ok(options);
    }

    // 3. option update
    @PostMapping("/{update_id}")
    public ResponseEntity<Option> updateOption(@PathVariable Long id, @Valid @RequestBody OptionRequest optionRequest) {
        Option option = optionService.updateOption(id, optionRequest);
        return ResponseEntity.ok(option);
    }

    // 4. option delete
    @DeleteMapping("/{delete_id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}
