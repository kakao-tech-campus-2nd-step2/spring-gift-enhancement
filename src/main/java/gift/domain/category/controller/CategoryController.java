package gift.domain.category.controller;

import gift.domain.category.dto.CategoryRequest;
import gift.domain.category.dto.CategoryResponse;
import gift.domain.category.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<CategoryResponse> categoryPage = categoryService.getAllCategories(pageNo, pageSize);
        return ResponseEntity.ok(categoryPage);
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
        @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
