package gift.controller;

import gift.dto.ProductCategoryRequest;
import gift.service.ProductCategoryService;
import gift.service.page.PageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final PageService pageService;

    public ProductCategoryController(ProductCategoryService productCategoryService, PageService pageService) {
        this.productCategoryService = productCategoryService;
        this.pageService = pageService;
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
}
