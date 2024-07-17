package gift.controller;

import gift.domain.model.dto.ProductResponseDto;
import gift.domain.model.enums.ProductSortBy;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Page<ProductResponseDto> productPage = productService.getAllProducts(0, ProductSortBy.ID_DESC);
        model.addAttribute("productPage", productPage);
        return "index";
    }
}