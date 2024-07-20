package gift.controller;

import gift.dto.ProductRequest;
import gift.exception.InvalidProductException;
import gift.exception.ProductNotFoundException;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProducts(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("productPage", productPage);
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "product-list";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
        @RequestParam int price,
        @RequestParam String imageUrl,
        @RequestParam Long categoryId,
        @RequestParam List<String> optionNames,
        @RequestParam List<Long> optionQuantities,
        RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(categoryId);
        Product product = new Product(name, price, imageUrl, category);
        productService.addProduct(product);

        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionNames.size(); i++) {
            String optionName = optionNames.get(i);
            Long optionQuantity = optionQuantities.get(i);
            Option option = new Option(optionName, optionQuantity, product);
            options.add(option);
        }
        optionService.addOption(options);

        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam String name,
                                @RequestParam int price,
                                @RequestParam String imageUrl,
                                @RequestParam Long categoryId,
                                @ModelAttribute Product product,
                                @ModelAttribute Option option,
                                @RequestParam("optionNames") List<String> optionNames,
                                @RequestParam("optionQuantities") List<Long> optionQuantities,
                                RedirectAttributes redirectAttributes) {

        List<Option> options = new ArrayList<>();
        Category category = categoryService.getCategoryById(categoryId);
        Product product1 = new Product(name, price, imageUrl, category);
        for (int i = 0; i < optionNames.size(); i++) {
            Option newOption = new Option(optionNames.get(i), optionQuantities.get(i), product1);
            options.add(newOption);
        }

        options.stream().forEach(eachOptions -> optionService.updateOption(option.getId(),eachOptions));



        product1.setOptions(options);
        productService.updateProduct(product.getId(), product1);

        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        return "redirect:/products";
    }

    @GetMapping("/view/{id}")
    public String getProductDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return "product-detail"; // product-details.html 뷰 반환
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
            return "redirect:/products";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}