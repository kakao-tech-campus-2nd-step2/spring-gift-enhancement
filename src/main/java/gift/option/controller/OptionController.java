package gift.option.controller;

import gift.option.dto.OptionDto;
import gift.option.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping("/{productId}/options")
    public List<OptionDto> getOptionsByProductId(@PathVariable Long id) {
        return optionService.getOptionsByProductId(id);
    }

    @PostMapping("/{productId}/options")
    public OptionDto addOptionToProduct(@PathVariable Long id, @RequestBody OptionDto optionDto) {
        return optionService.addOptionToProduct(id, optionDto);
    }

}