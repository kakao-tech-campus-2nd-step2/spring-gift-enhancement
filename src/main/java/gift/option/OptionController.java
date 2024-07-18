package gift.option;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{productID}/options")
    public List<OptionResponse> getProductOptions(@PathVariable Long productID){
        return optionService.findAllProductOptions(productID);
    }

    @GetMapping("/{optionID}")
    public OptionResponse getProductOption(@PathVariable Long optionID){
        return optionService.findByOptionID(optionID);
    }

}
