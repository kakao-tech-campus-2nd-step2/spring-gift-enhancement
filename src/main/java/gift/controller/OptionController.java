package gift.controller;

import gift.entity.Option;
import gift.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping
    public Set<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    @GetMapping("/{id}")
    public Optional<Option> getOptionById(@PathVariable Long id) {
        return optionService.getOptionById(id);
    }

    @PostMapping
    public Option createOption(@RequestBody Option option) {
        return optionService.saveOption(option);
    }

    @PutMapping("/{id}")
    public Option updateOption(@PathVariable Long id, @RequestBody Option optionDetails) {
        return optionService.updateOption(id, optionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
    }
}
