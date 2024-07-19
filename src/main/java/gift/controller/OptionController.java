package gift.controller;

import gift.dto.OptionDTO;
import gift.model.Option;
import gift.model.Product;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;
    private final ProductService productService;

    public OptionController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping
    public String listOptions(@PathVariable Long productId, Model model) {
        Product product = productService.findProductsById(productId);
        List<Option> options = optionService.findOptionsByProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("options", options);
        return "option_list";
    }

    @GetMapping("/add")
    public String showAddOptionForm(@PathVariable Long productId, Model model) {
        model.addAttribute("optionDTO", new OptionDTO("", null, productId));
        return "add_option_form";
    }

    @PostMapping("/add")
    public String addOption(@PathVariable Long productId, @ModelAttribute @Valid OptionDTO optionDTO,
        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_option_form";
        }
        try {
            optionService.saveOption(optionDTO);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.optionDTO", e.getMessage());
            return "add_option_form";
        }
        return "redirect:/admin/products/" + productId + "/options";
    }

    @GetMapping("/edit/{optionId}")
    public String showEditOptionForm(@PathVariable Long productId, @PathVariable Long optionId, Model model) {
        Option option = optionService.findOptionByIdAndProductId(optionId, productId);
        model.addAttribute("optionDTO", OptionService.toDTO(option));
        model.addAttribute("optionId", optionId);
        return "edit_option_form";
    }

    @PutMapping("/edit/{optionId}")
    public String editOption(@PathVariable Long productId, @PathVariable Long optionId, @ModelAttribute @Valid OptionDTO optionDTO,
        BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("optionId", optionId);
            return "edit_option_form";
        }
        try {
            optionService.updateOption(optionId, optionDTO);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.optionDTO", e.getMessage());
            model.addAttribute("optionId", optionId);
            return "edit_option_form";
        }
        return "redirect:/admin/products/" + productId + "/options";
    }

    @DeleteMapping("/delete/{optionId}")
    public String deleteOption(@PathVariable Long productId, @PathVariable Long optionId, Model model) {
        try {
            optionService.deleteOption(productId, optionId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("deleteError", e.getMessage());
            return listOptions(productId, model);
        }
        return "redirect:/admin/products/" + productId + "/options";
    }
}
