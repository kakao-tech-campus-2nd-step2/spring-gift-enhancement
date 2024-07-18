package gift.controller.rest;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Category> postCategory(@RequestBody CategoryDTO form) {
        Category result = categoryService.save(form);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category result = categoryService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> putCategory(@PathVariable Long id, @RequestBody CategoryDTO form) {
        Category result = categoryService.update(id, form);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "deleted successfully");
        return ResponseEntity.ok().body(response);
    }
}
