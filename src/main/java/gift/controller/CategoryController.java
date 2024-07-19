package gift.controller;

import gift.DTO.Category.CategoryRequest;
import gift.DTO.Category.CategoryResponse;
import gift.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("api/categories")
    public ResponseEntity<Page<CategoryResponse>> readCategory(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Page<CategoryResponse> categories = categoryService.findAll(page, size);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping("/api/categories")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest category){
        categoryService.save(category);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/api/categories/{category_id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long category_id, @RequestBody CategoryRequest category
    ){
        categoryService.update(category_id, category);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/api/categories/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long category_id){
        categoryService.delete(category_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
