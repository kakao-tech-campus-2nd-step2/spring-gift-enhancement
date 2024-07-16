package gift.controller;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "name,asc") String[] sort) {
        Page<ProductResponseDTO> productPage = productService.getProducts(page, size, sort);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("productRequestDTO", new ProductRequestDTO());
        return "productForm";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("productRequestDTO") ProductRequestDTO productRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }

        if (productRequestDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            return "productForm";
        }

        productService.createProduct(productRequestDTO);
        return "redirect:/api/products"; // 리디렉션 설정
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        model.addAttribute("productRequestDTO", productResponseDTO);
        return "productForm";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productRequestDTO") ProductRequestDTO updatedProductDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }

        if (updatedProductDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            return "productForm";
        }

        productService.updateProduct(id, updatedProductDTO);
        return "redirect:/api/products"; // 리디렉션 설정
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products"; // 리디렉션 설정
    }
}
