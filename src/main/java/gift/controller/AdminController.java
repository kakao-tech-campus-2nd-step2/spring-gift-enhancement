package gift.controller;

import gift.dto.ProductDto;
import gift.exception.ProductNotFoundException;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/product/list")
    public String listProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "list"; // list.html 파일 보여주기
    }

    @GetMapping("/product/view/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        model.addAttribute("product", product);
        return "view"; // view.html 파일 보여주기
    }

    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "add"; // add.html 파일 보여주기
    }

    @PostMapping("/product/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add"; // 에러가 있으면 다시 add.html 보여주기
        }
        productService.save(productDto);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/product/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
        model.addAttribute("productDto", new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImgUrl()));
        return "edit";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        productService.update(id, productDto);
        return "redirect:/admin/product/list";
    }
}