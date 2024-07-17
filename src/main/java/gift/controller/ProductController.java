package gift.controller;

import gift.domain.Category;
import gift.dto.request.ProductRequest;
import gift.dto.response.CategoryResponse;
import gift.dto.response.ProductResponse;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model){
        List<CategoryResponse> categories = categoryService.findAll(); // 모든 카테고리를 가져오는 서비스 메서드
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductRequest(null,null, 0, null, null));
        return "products/add";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute ProductRequest productDto) {
        productService.save(productDto);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model){
        List<CategoryResponse> categories = categoryService.findAll(); // 모든 카테고리를 가져오는 서비스 메서드
        model.addAttribute("categories", categories);

        ProductResponse productDto = productService.findById(id);
        model.addAttribute("productDto", new ProductRequest(productDto.id(),productDto.name(), productDto.price(), productDto.imageUrl(), productDto.categoryResponse().id()));
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductRequest productDto){
        productService.updateById(id, productDto);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}