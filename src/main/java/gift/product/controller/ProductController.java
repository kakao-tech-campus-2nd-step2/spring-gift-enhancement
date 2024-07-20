package gift.product.controller;

import gift.option.domain.Option;
import gift.option.domain.OptionRequest;
import gift.product.model.Product;
import gift.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/products")
// crud를 진행하고 다시 api/products로 보내는 역할
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null || text.trim().isEmpty() ? null : text);
            }
        });
    }

    // 1. product create
    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product, product.getCategory().getId());
        return "redirect:/api/products";
    }

    // 2. product update
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product, product.getCategory().getId());
        return "redirect:/api/products";
    }

    // 3. product delete
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }

    // product pagination
    @GetMapping("/page")
    @ResponseBody
    public Page<Product> getProductsByPage(@RequestParam int page,
                                           @RequestParam int size,
                                           @RequestParam(defaultValue = "price") String sortBy,
                                           @RequestParam(defaultValue = "desc") String direction) {
        return productService.getProductsByPage(page, size, sortBy, direction);
    }
}
