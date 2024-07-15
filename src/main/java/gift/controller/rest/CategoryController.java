package gift.controller.rest;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Category result = categoryService.findOne(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping()
    public ResponseEntity<Category> putCategory(@RequestBody CategoryDTO form) {
        Category result = categoryService.update(form);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().body("deleted successfully");
    }
}
