package gift.controller;

import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.model.category.CategoryService;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> registerCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.register(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
