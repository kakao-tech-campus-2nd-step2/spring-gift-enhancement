package gift.controller;

import gift.dto.ProductPageResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import gift.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        ProductPageResponseDto productPage = productService.getAllProducts(page, size);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductRequestDto("", 0, "", 0L));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product_form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductRequestDto productRequestDTO) {
        productService.addProduct(productRequestDTO);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product_edit_form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute ProductRequestDto productRequestDTO) {
        productService.updateProduct(id, productRequestDTO);
        return "redirect:/products";
    }
}
