package gift.product.controller;


import gift.product.dto.CategoryDto;
import gift.product.model.Category;
import gift.product.service.CategoryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategoryAll() {
        List<Category> categories = categoryService.getCategoryAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/insert")
    public ResponseEntity<Category> insertCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.insertCategory(categoryDto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
