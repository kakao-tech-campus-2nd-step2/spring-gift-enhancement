package gift.controller;

import gift.domain.CategoryName;
import gift.dto.OptionDTO;
import gift.dto.ProductDTO;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
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
    public List<ProductDTO> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{productId}/options")
    public ResponseEntity<List<OptionDTO>> getOptions(@PathVariable Long productId) {
        List<OptionDTO> options = optionService.getOptionsByProductId(productId);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<OptionDTO> addOption(@PathVariable Long productId,
        @Valid @RequestBody OptionDTO optionDTO) {
        OptionDTO savedOption = optionService.addOption(productId, optionDTO);
        return ResponseEntity.ok(savedOption);
    }

    @GetMapping("/paged")
    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @PutMapping("/{id}/category")
    public ResponseEntity<ProductDTO> updateProductCategory(@PathVariable Long id, @RequestParam CategoryName categoryName) {
        ProductDTO updatedProduct = productService.updateProductCategory(id, categoryName);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
