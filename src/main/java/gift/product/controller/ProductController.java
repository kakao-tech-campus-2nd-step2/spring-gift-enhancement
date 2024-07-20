package gift.product.controller;

import gift.category.service.CategoryService;
import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ProductRepository productRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.save(productDto);
    }

    @PutMapping("/{productId}")
    public void editProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto productDto) {
        productService.update(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
    }

    // 상품 추가 폼 뷰
    @GetMapping("/admin/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add";
    }

    /**
     * 페이지네이션을 위한 새로운 엔드포인트
     * 특정 페이지와 크기 요청: /api/products?page=1&size=5
     * 페이지 번호: 0
     * 페이지 크기: 10
     * 정렬: 이름을 기준으로 오름차순 정렬 (기본값)
     **/
    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        int maxSize = 50;
        size = Math.min(size, maxSize);

        String sortBy = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return productRepository.findAll(pageable);
    }

    @GetMapping("/{productId}")
    public Optional<Product> getId(@PathVariable Long id) {
        return productRepository.findById(id);
    }
}