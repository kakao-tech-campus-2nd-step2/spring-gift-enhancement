package gift.controller;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProducts(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "name,asc") String[] sort) {
        model.addAttribute("productPage", productService.getProducts(page, size, sort));
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("productFormDTO", new ProductRequestDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productForm";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("productFormDTO") ProductRequestDTO productRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "productForm";
        }

        if (productRequestDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "productForm";
        }

        productService.createProduct(productRequestDTO);
        return "redirect:/api/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        model.addAttribute("productFormDTO", productResponseDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productForm";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productFormDTO") ProductRequestDTO productRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "productForm";
        }

        if (productRequestDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "productForm";
        }

        productService.updateProduct(id, productRequestDTO);
        return "redirect:/api/products";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }
}
