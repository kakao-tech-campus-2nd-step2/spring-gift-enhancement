package gift.option;

import gift.option.model.OptionRequestDto;
import gift.option.model.OptionResponseDto;
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
    public List<OptionResponseDto> getAllOption(@PathVariable("id") Long productId) {
        return optionService.getOptions(productId);
    }

    @PostMapping("/api/products/{id}/options")
    public Long addOption(@PathVariable("id") Long productId,
        @Valid @RequestBody OptionRequestDto optionRequestDto) {
        return optionService.addOption(productId, optionRequestDto);
    }

    @PutMapping("/api/products/{productId}/options/{optionId}")
    public void updateOption(@PathVariable("optionId") Long optionId,
        @Valid @RequestBody OptionRequestDto optionRequestDto) {
        optionService.updateOption(optionId, optionRequestDto);
    }

    @DeleteMapping("/api/products/{productId}/options/{optionId}")
    public void deleteOption(@PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId) {
        optionService.deleteOption(productId, optionId);
    }
}
