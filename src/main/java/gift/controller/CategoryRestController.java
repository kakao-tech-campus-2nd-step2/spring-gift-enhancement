package gift.controller;

import gift.dto.request.CategoryRequest;
import gift.dto.response.CategoryResponse;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        categoryService.updateById(id,categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
