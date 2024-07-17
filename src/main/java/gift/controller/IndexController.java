package gift.controller;

import gift.dto.OptionDTO;
import gift.dto.ProductDTO;
import gift.model.CategoryName;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {
    private final ProductService productService;

    public IndexController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/postform")
    public String postform(Model model){
        List<String> categoryList = CategoryName.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "postform";
    }

    @PostMapping("/editform/{id}")
    public String editform(@PathVariable Long id, Model model){
        ProductDTO product = productService.getProductDTOById(id);
        model.addAttribute("product", product);
        List<String> categoryList = CategoryName.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "editform";
    }

    @PostMapping("/option/{productId}")
    public String option(@PathVariable Long productId, Model model){
        OptionDTO options = productService.getOptions(productId);
        List<String> optionList = options.getOption();
        model.addAttribute("optionList", optionList);
        model.addAttribute("optionId", options.getId());
        return "option";
    }
}
