package gift.controller;

import gift.dto.CategoryRequest;
import gift.model.Category;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity findAllCategories() {
        List<Category> categoryList = categoryService.findAllCategories();
        return ResponseEntity.ok().body(categoryList);
    }

    @PutMapping("/categories")
    public ResponseEntity updateCategory(CategoryRequest request) {
        categoryService.updateCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
