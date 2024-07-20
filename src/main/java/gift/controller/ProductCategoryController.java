package gift.controller;

import gift.dto.ProductCategoryRequest;
import gift.dto.ProductCategoryResponse;
import gift.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCategory(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        var productCategory = productCategoryService.addCategory(productCategoryRequest);
        return ResponseEntity.created(URI.create("/api/categories/" + productCategory.id())).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        productCategoryService.updateCategory(id, productCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getCategory(@PathVariable Long id) {
        var productCategory = productCategoryService.getCategory(id);
        return ResponseEntity.ok(productCategory);
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getCategories(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var productCategories = productCategoryService.getCategories(pageable);
        return ResponseEntity.ok(productCategories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        productCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
