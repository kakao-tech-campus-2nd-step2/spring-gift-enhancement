package gift.controller.admin;

import gift.dto.category.CategoryResponse;
import gift.dto.product.ProductCreateRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.ProductUpdateRequest;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ProductAdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductAdminController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllProducts(
        Model model,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ProductResponse> productPage = productService.getAllProducts(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductCreateRequest("", 0, "", null));
        return "product_form";
    }

    @PostMapping
    public String addProduct(
        @Valid @ModelAttribute("product") ProductCreateRequest productCreateRequest,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            List<CategoryResponse> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "product_form";
        }
        productService.addProduct(productCreateRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        ProductResponse productResponse = productService.getProductById(id);
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", productResponse);
        return "product_edit";
    }

    @PutMapping("/{id}")
    public String updateProduct(
        @PathVariable("id") Long id,
        @Valid @ModelAttribute ProductUpdateRequest productUpdateRequest, BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            List<CategoryResponse> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", productUpdateRequest);
            model.addAttribute("org.springframework.validation.BindingResult.product", result);
            return "product_edit";
        }
        productService.updateProduct(id, productUpdateRequest);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
