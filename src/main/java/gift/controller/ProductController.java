package gift.controller;

import gift.dto.ProductDto;
import gift.entity.Category;
import gift.entity.Product;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> getProducts(Pageable pageable) {
        Page<Product> productPage = productService.getProducts(pageable);
        Map<String, Object> response = new HashMap<>();
        var data = productPage.getContent();
        response.put("content", data.stream().map(v -> {
            var dto = new ProductDto(v);
            dto.setCategoryName(v.getCategory().getName());
            return dto;
        }));
        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return response;
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
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
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto(product));
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "edit-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
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
