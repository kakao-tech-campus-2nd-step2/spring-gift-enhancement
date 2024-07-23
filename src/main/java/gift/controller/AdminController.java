package gift.controller;

import gift.dto.ProductDto;
import gift.exception.ProductNotFoundException;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "list"; // list.html 파일 보여주기
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        model.addAttribute("product", product);
        return "view"; // view.html 파일 보여주기
    }

    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "add"; // add.html 파일 보여주기
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        model.addAttribute("product", product);
        return "edit"; // edit.html 파일 보여주기
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @ModelAttribute("product") ProductDto productDto) {
        productService.update(id, productDto);
        return "redirect:/admin/products";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}