package gift.controller;

import gift.dto.ProductDto;
import gift.service.ProductService;
import gift.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> getProducts(Pageable pageable) {
        Page<Product> productPage = productService.getProducts(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("content", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return response;
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            return "add-product";
        }
        productService.addProduct(productDto);
        return "redirect:/view/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/view/products";
        }
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            return "edit-product";
        }
        productService.updateProduct(id, productDto);
        return "redirect:/view/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/view/products";
    }
}
