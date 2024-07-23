package gift.controller;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import gift.service.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OptionService optionService;
    private final WishlistService wishlistService;

    public HomeController(ProductService productService, CategoryService categoryService, OptionService optionService, WishlistService wishlistService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.optionService = optionService;
        this.wishlistService= wishlistService;
    }

    @GetMapping("/home")
    public String showHomeForm() {
        return "home";
    }


    @GetMapping("/products")
    public String showProductsPage() {
        return "products";
    }

    @GetMapping("/products/data")
    @ResponseBody
    public Map<String, Object> getProducts(Pageable pageable) {
        Page<Product> productPage = productService.getProducts(pageable);
        Map<String, Object> response = new HashMap<>();

        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            ProductDto dto = new ProductDto(product);
            productDtoList.add(dto);
        }

        response.put("content", productDtoList);

        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return response;
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-product";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/view/products";
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("options", optionService.getAllOptions());

        model.addAttribute("product", new ProductDto(product));
        return "edit-product";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/view/products";
    }

    @GetMapping("/productlist")
    public String showProductListsPage() {
        return "user-products";
    }


    @GetMapping("/wishlist/data")
    public ResponseEntity<Map<String, Object>> getWishlistItems(
            @RequestParam("email") String email,
            Pageable pageable) {
        Page<Product> productPage = wishlistService.getWishlistByEmail(email, pageable);
        Map<String, Object> response = new HashMap<>();
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            ProductDto dto = new ProductDto(product);
            productDtoList.add(dto);
        }

        response.put("content", productDtoList);
        response.put("currentPage", productPage.getNumber() + 1);
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wishlist")
    public String wishlistForm() {
        return "wishlist";
    }


}
