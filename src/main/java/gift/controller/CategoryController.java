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
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort,
            @RequestParam(value = "field", defaultValue = "id") String field
    ){
        if(sort.equals("asc")){
            Page<CategoryResponse> categories = categoryService.findAllASC(page, size, field);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        Page<CategoryResponse> categories = categoryService.findAllDESC(page, size, field);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping("/api/categories")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.save(categoryRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/api/categories/{category_id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long category_id, @RequestBody CategoryRequest categoryRequest
    ){
        categoryService.update(category_id, categoryRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/api/categories/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long category_id){
        categoryService.delete(category_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
