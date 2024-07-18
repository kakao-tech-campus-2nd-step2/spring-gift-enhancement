package gift.option;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/api/products/{id}/options")
    public List<OptionResponse> getAllOption(@PathVariable("id") Long productId) {
        return optionService.getOptions(productId);
    }

    @PostMapping("/api/products/{id}/options")
    public Long addOption(@PathVariable("id") Long productId,
        @Valid @RequestBody OptionRequest optionRequest) {
        return optionService.addOption(productId, optionRequest);
    }

    @PutMapping("/api/options/{id}")
    public void updateOption(@PathVariable("id") Long optionId,
        @Valid @RequestBody OptionRequest optionRequest) {
        optionService.updateOption(optionId, optionRequest);
    }

    @DeleteMapping("/api/options/{id}")
    public void deleteOption(@PathVariable("id") Long optionId) {
        optionService.deleteOption(optionId);
    }
}
