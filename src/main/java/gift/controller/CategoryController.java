package gift.controller;

import gift.request.CategoryRequest;
import gift.response.CategoryResponse;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> categoryList() {
        return ResponseEntity.ok()
                .body(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<Void> categoryAdd(@RequestBody @Valid CategoryRequest request) {
        categoryService.addCategory(request);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> categoryEdit(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest request) {
        categoryService.editCategory(categoryId, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> categoryRemove(@PathVariable Long categoryId) {
        categoryService.removeCategory(categoryId);

        return ResponseEntity.ok()
                .build();
    }

}
