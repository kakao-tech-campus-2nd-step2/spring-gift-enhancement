package gift.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import gift.model.Product;
import gift.service.CategoryService;
import gift.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    
    public AdminController(ProductService productService, CategoryService categoryService) {
    	this.productService = productService;
    	this.categoryService =categoryService;
    }

    @GetMapping
    public String adminPage(Model model,
    		@PageableDefault(sort="name") Pageable pageable) {
        Page<Product> productList = productService.getProducts(pageable);
        model.addAttribute("products", productList);
        return "admin";
    }

    @GetMapping("/new")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product("", 0, "", null));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    @PostMapping
    public String addProduct(@ModelAttribute Product product, BindingResult bindingResult) {
        productService.createProduct(product, bindingResult);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @ModelAttribute Product updatedProduct, BindingResult bindingResult) {
        productService.updateProduct(id, updatedProduct, bindingResult);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
