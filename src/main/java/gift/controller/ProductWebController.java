package gift.controller;

import gift.domain.Category;
import gift.domain.CategoryName;
import gift.domain.Product;
import gift.service.CategoryService;
import gift.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/web/products")
public class ProductWebController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductWebController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProductsPage(Model model) {
        List<Product> products = productService.findAllProducts();
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "products";
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String postProduct(@RequestParam String name, @RequestParam BigDecimal price,
        @RequestParam String imageUrl, @RequestParam String description,
        @RequestParam CategoryName categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        Product product = new Product.ProductBuilder()
            .name(name)
            .price(price)
            .imageUrl(imageUrl)
            .description(description)
            .category(category)
            .build();
        productService.createProduct(product);
        return "redirect:/web/products";
    }

    @PostMapping(value = "/delete", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String deleteProduct(@RequestParam List<Long> productIds) {
        for (Long id : productIds) {
            productService.deleteProduct(id);
        }
        return "redirect:/web/products";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "productEdit";
    }

    @PostMapping(value = "/edit/{id}", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String editProduct(@PathVariable Long id, @RequestParam String name,
        @RequestParam BigDecimal price, @RequestParam String imageUrl,
        @RequestParam CategoryName categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        Product product = new Product.ProductBuilder()
            .id(id)
            .name(name)
            .price(price)
            .imageUrl(imageUrl)
            .category(category)
            .build();
        productService.updateProduct(id, product);
        return "redirect:/web/products";
    }
}
