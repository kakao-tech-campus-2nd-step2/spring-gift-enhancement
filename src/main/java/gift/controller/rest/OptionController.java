package gift.controller.rest;

import gift.entity.Option;
import gift.entity.OptionDTO;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping()
    public ResponseEntity<List<Option>> getAllOptions() {
        return ResponseEntity.ok().body(optionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(optionService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Option> createOption(@RequestBody @Valid OptionDTO optionDTO) {
        return ResponseEntity.ok().body(optionService.save(optionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable Long id, @RequestBody @Valid OptionDTO optionDTO) {
        return ResponseEntity.ok().body(optionService.update(id, optionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOption(@PathVariable Long id) {
        optionService.delete(id);
        Map<String, String> result = new HashMap<>();
        result.put("message", "deleted successfully");
        return ResponseEntity.ok().body(result);
    }
}
