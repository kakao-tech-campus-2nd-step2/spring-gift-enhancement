package gift.category.controller;

import gift.category.model.dto.CategoryResponse;
import gift.category.service.CategoryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.findCategoryById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        List<CategoryResponse> response = categoryService.findAllCategories();
        return ResponseEntity.ok().body(response);
    }
}
