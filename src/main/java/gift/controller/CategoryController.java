package gift.controller;

import gift.dto.CategoryDTO;
import gift.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> readCategory(@RequestParam int page, @RequestParam int size) {
        Page<CategoryDTO> categories = categoryService.findAll(page, size);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("category_id") Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category_id") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}