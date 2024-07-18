package gift.product.controller;

import gift.product.service.CategoryService;
import gift.product.service.OptionService;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminOptionController {

    private final OptionService optionService;
    private final ProductService productService;

    @Autowired
    public AdminOptionController(
        OptionService optionService,
        ProductService productService
    ) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public String getAllOptions(@PathVariable Long productId, Model model, Pageable pageable) {
        System.out.println("[ApiOptionController] getAllOptions()");
        model.addAttribute("optionList", optionService.getAllOptions(productId, pageable));
        model.addAttribute("product", productService.findById(productId));
        return "product-option";
    }

}
