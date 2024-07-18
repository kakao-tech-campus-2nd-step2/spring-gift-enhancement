package gift.controller;

import gift.dto.OptionDTO;
import gift.dto.PageRequestDTO;
import gift.dto.ProductDTO;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/products")
@Validated
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OptionService optionService;

    public ProductController(ProductService productService, CategoryService categoryService, OptionService optionService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.optionService = optionService;
    }

    @GetMapping
    public String allProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Min(1) @Max(30) int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        Model model) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO(page, size, sortBy, direction);
        Page<ProductDTO> productPage = productService.findAllProducts(pageRequestDTO);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "Products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "Add_product";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "Add_product";
        }
        productService.addProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Optional<ProductDTO> productDTO = productService.findProductById(id);
        if (productDTO.isEmpty()) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", productDTO.get());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "Edit_product";
    }

    @PutMapping("/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "Edit_product";
        }
        productService.updateProduct(productDTO);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/options")
    public String manageProductOptions(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProductById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<OptionDTO> productOptions = productDTO.getOptions();
        List<OptionDTO> allOptions = optionService.findAllOptions();

        model.addAttribute("product", productDTO);
        model.addAttribute("options", allOptions.stream()
            .filter(option -> productOptions.stream().anyMatch(productOption -> productOption.getId().equals(option.getId())))
            .collect(Collectors.toList()));

        return "Manage_product_options";
    }

    @PostMapping("/{id}/options")
    public String updateProductOptions(@PathVariable Long id, @RequestParam List<Long> optionIds) {
        productService.updateProductOptions(id, optionIds);
        return "redirect:/admin/products";
    }
}