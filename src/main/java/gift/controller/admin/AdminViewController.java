package gift.controller.admin;

import gift.DTO.ProductResponse;
import gift.domain.Product;
import gift.DTO.ProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductManagementPage(Model model) {
        try {
            List<ProductResponse> products = productService.getAllProducts();
            if (products == null) {
                model.addAttribute("products", List.of());
                return "productManagement";
            }
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products: " + e.getMessage());
        }
        return "productManagement";
    }

    @GetMapping("/product/add")
    public String showProductAddForm(Model model) {
        model.addAttribute("product", new ProductRequest("", 0, ""));
        return "productAddForm";
    }

    @PostMapping("/product/add")
    public String saveProduct(
        @ModelAttribute @Valid ProductRequest newProduct,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "productAddForm";
        }
        productService.addProduct(newProduct);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String showProductForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("product", productService.getProductById(id));
        } catch (RuntimeException e) {
            return "redirect:/admin";
        }

        return "productEditForm";
    }

    @PostMapping("/product/edit/{id}")
    public String showProductForm(
        @ModelAttribute @Valid ProductRequest updateProduct,
        Model model
    ) {
        try {
            model.addAttribute("product", updateProduct);
        } catch (RuntimeException e) {
            return "productEditForm";
        }

        return "redirect:/admin";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
}
