package gift.controller;

import gift.dto.OptionDTO;
import gift.service.OptionService;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/options")
public class OptionController {
    private final OptionService optionService;
    private final ProductService productService;

    public OptionController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @PostMapping("/{productId}")
    public String option(@PathVariable Long productId, Model model){
        OptionDTO options = productService.getOptions(productId);
        model.addAttribute("options", options);
        model.addAttribute("productId", productId);
        return "option";
    }

    @PostMapping("/add/{optionId}/{productId}")
    public String addOption(@PathVariable Long optionId, @PathVariable Long productId, @RequestParam("option") String newOption, Model model) {
        optionService.addOption(optionId, newOption);
        OptionDTO options = productService.getOptions(productId);
        model.addAttribute("options", options);
        model.addAttribute("productId", productId);
        return "option";
    }

    @PostMapping("/update/{optionId}/{productId}/{oldName}")
    public String updateOption(@PathVariable Long optionId, @PathVariable Long productId, @PathVariable String oldName, @RequestParam("newName") String newName, Model model) {
        optionService.updateOption(optionId, oldName, newName);
        OptionDTO options = productService.getOptions(productId);
        model.addAttribute("options", options);
        model.addAttribute("productId", productId);
        return "option";
    }

    @GetMapping("/delete/{optionId}/{productId}/{optionName}")
    public String deleteOption(@PathVariable Long optionId, @PathVariable Long productId, @PathVariable String optionName, Model model) {
        optionService.deleteOption(optionId, optionName);
        OptionDTO options = productService.getOptions(productId);
        model.addAttribute("options", options);
        model.addAttribute("productId", productId);
        return "option";
    }
}
