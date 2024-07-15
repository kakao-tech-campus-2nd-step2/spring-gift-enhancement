package gift.controller.api;

import gift.dto.request.AddCategoryRequest;
import gift.dto.request.UpdateCategoryRequest;
import gift.dto.response.CategoryIdResponse;
import gift.dto.response.CategoryResponse;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/api/categories")
    public ResponseEntity<CategoryIdResponse> addCategory(@RequestBody AddCategoryRequest request) {
        CategoryIdResponse categoryIdResponse = categoryService.addCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryIdResponse);
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PutMapping("/api/categories")
    public ResponseEntity<Void> updateCategory(@RequestBody UpdateCategoryRequest request) {
        categoryService.updateCategory(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
