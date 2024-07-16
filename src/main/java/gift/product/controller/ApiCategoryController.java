package gift.product.controller;

import gift.product.model.Category;
import gift.product.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class ApiCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public ApiCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<String> registerCategory(@Valid @RequestBody Category category) {
        System.out.println("[CategoryController] registerCategory()");
        categoryService.registerCategory(category.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Category registered successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        System.out.println("[CategoryController] updateCategory()");
        categoryService.updateCategory(new Category(id, category.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Category registered successfully");
    }

}
