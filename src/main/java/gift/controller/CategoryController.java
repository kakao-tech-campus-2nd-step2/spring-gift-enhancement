package gift.controller;

import gift.dto.CategoryDto;
import gift.service.CategoryService;
import gift.vo.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = service.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDto categoryDto) {
        service.addCategory(categoryDto.toCategory());
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDto categoryDto) {
        service.updateCategory(categoryDto.toCategory());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") Long id) {
        service.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
