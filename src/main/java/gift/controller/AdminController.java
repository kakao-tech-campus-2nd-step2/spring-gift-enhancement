package gift.controller;

import gift.dto.ProductDTO;
import gift.model.Product;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProducts(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Product> products = productService.findAllProducts(pageable);
        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO("", "0", null, ""));
        model.addAttribute("categories", categoryService.findAllCategories());
        return "add_product_form";
    }

    @PostMapping
    public String addProduct(@ModelAttribute @Valid ProductDTO productDTO, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "add_product_form";
        }
        productService.saveProduct(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}")
    public String showEditProductForm(@PathVariable("id") long id, Model model) {
        Product product = productService.findProductsById(id);
        model.addAttribute("productDTO", ProductService.toDTO(product));
        model.addAttribute("productID", id);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "edit_product_form";
    }

    @PutMapping("/{id}")
    public String editProduct(@PathVariable("id") long id,
        @ModelAttribute @Valid ProductDTO updatedProductDTO,
        BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productID", id);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "edit_product_form";
        }
        productService.updateProduct(updatedProductDTO, id);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteProductAndWishlist(id);
        return "redirect:/admin/products";
    }

}
