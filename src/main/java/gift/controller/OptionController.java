package gift.controller;

import gift.dto.OptionDTO;
import gift.dto.ProductDTO;
import gift.service.OptionService;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;
    private final ProductService productService;

    @Autowired
    public OptionController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping
    public String getOptionsByProductId(@PathVariable Long productId, Model model) {
        List<OptionDTO> options = optionService.getOptionsByProductId(productId);
        ProductDTO product = productService.getProductById(productId);
        model.addAttribute("options", options);
        model.addAttribute("product", product);
        return "productOption";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public OptionDTO getOptionById(@PathVariable Long id) {
        return optionService.getOptionById(id);
    }

    @PostMapping
    @ResponseBody
    public void addOption(@RequestBody OptionDTO optionDTO) {
        optionService.saveOption(optionDTO);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateOption(@PathVariable Long id, @RequestBody OptionDTO optionDTO) {
        optionService.updateOption(id, optionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
    }
}
