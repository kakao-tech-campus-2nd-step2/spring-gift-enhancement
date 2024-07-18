package gift.controller;

import gift.dto.OptionDTO;
import gift.service.OptionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{id}/options")
    public List<OptionDTO> getOptions(@PathVariable("id") long productId) {
        return optionService.getOptions(productId);
    }
}
