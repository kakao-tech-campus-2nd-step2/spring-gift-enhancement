package gift.controller;

import gift.dto.CategoryRequest;
import gift.dto.CategoryResponse;
import gift.entity.Category;
import gift.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponse> response = categories.stream()
            .map(CategoryResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(
        @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.addCategory(categoryRequest);
        return ResponseEntity.status(201).body(CategoryResponse.from(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryService.findById(id).isPresent()) {
            return ResponseEntity.status(204).build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
