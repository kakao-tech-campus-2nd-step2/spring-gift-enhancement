package gift.option;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{id}/options")
    public List<OptionResponse> getProductOptions(@PathVariable Long id){
        return optionService.findAllProductOptions(id);
    }

    @PostMapping("/{id}/options")
    public OptionResponse addProductOption(@PathVariable Long id, @Valid @RequestBody OptionRequest optionRequest){
        return optionService.insertProductNewOption(id, optionRequest);
    }

    @PutMapping("/{id}/options/{optionId}")
    public OptionResponse updateProductOption(@PathVariable Long id, @PathVariable Long optionId, @RequestBody OptionRequest optionRequest){
        return optionService.updateOption(id, optionId, optionRequest);
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public void deleteProductOption(@PathVariable Long id, @PathVariable Long optionId){
        optionService.deleteOption(id, optionId);
    }
}
