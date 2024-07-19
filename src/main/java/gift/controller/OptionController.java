package gift.controller;

import gift.dto.OptionDTO;
import gift.service.OptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{id}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public List<OptionDTO> getOptions(@PathVariable("id") long productId) {
        return optionService.getOptions(productId);
    }

    @PostMapping
    public OptionDTO addOption(@PathVariable("id") long productId, @Valid @RequestBody OptionDTO optionDTO) {
        return optionService.addOption(productId, optionDTO);
    }
}
