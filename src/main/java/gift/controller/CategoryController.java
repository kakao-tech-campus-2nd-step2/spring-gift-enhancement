package gift.controller;

import gift.domain.CategoryRequest;
import gift.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<String> create(
            @RequestParam("categoryName") String categoryName
    ) {
        CategoryRequest categoryRequest = new CategoryRequest(null, categoryName);
        categoryService.create(categoryRequest);
        return ResponseEntity.ok().body("successfully created");
    }

    @PutMapping
    public ResponseEntity<String> update(
            @RequestParam("id") Long categoryId,
            @RequestParam("categoryName") String categoryName
    ) {
        CategoryRequest categoryRequest = new CategoryRequest(categoryId, categoryName);
        categoryService.update(categoryRequest);
        return ResponseEntity.ok().body("successfully created");
    }
}
