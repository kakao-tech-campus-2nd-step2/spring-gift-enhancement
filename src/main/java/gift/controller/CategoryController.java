package gift.controller;


import gift.dto.CategoryDTO;
import gift.dto.CategoryResponse;
import gift.service.CategoryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.readAll());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);

        return ResponseEntity.ok().build();
    }
}
