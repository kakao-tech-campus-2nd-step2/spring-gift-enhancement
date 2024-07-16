package gift.ui.admin;

import gift.api.category.Category;
import gift.api.category.CategoryRepository;
import gift.api.product.Product;
import gift.api.product.ProductRepository;
import gift.api.product.ProductRequest;
import gift.global.exception.NoSuchEntityException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public AdminController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public String view(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("productDto", new ProductRequest());
        return "administrator";
    }

    @PostMapping("/add")
    public RedirectView add(@Valid ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new NoSuchEntityException("category"));
        Product product = new Product(category, productRequest.getName(),
            productRequest.getPrice(), productRequest.getImageUrl());
        productRepository.save(product);
        return new RedirectView("/api/products");
    }

    @PutMapping("/update/{id}")
    public RedirectView update(@PathVariable("id") long id, @Valid ProductRequest productRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchEntityException("product"));
        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new NoSuchEntityException("category"));
        product.update(category, productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
        return new RedirectView("/api/products");
    }

    @DeleteMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") long id) {
        productRepository.deleteById(id);
        return new RedirectView("/api/products");
    }
}
