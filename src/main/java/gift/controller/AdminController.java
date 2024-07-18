package gift.controller;

import gift.common.dto.PageResponse;
import gift.model.category.CategoryResponse;
import gift.model.product.CreateProductRequest;
import gift.model.product.ProductResponse;
import gift.model.product.UpdateProductRequest;
import gift.service.CategoryService;
import gift.service.ProductService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    AdminController(ProductService productService, CategoryService  categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/product")
    public String registerProductForm(Model model) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        PageResponse<CategoryResponse> categoryList = categoryService.findAllCategory(pageRequest);
        List<CategoryResponse> categories = categoryList.responses();
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    @PostMapping("/admin/product")
    public String registerProduct(CreateProductRequest createProductRequest) {
        productService.addProduct(createProductRequest);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<ProductResponse> response = productService.findAllProduct(pageable);
        model.addAttribute("products", response);
        return "productList";
    }

    @GetMapping("/admin/product/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productService.findProduct(id);
        return response;
    }

    @GetMapping("/product")
    public String updateProductForm(@RequestParam("id") Long id) {
        return "editProduct";
    }

    @PutMapping("/admin/product/{id}")
    public String updateProduct(@PathVariable("id") Long id, UpdateProductRequest request) {
        productService.updateProduct(id, request);
        return "redirect:/products";
    }

    @DeleteMapping("/admin/product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
