package gift.controller;

import gift.domain.category.Category;
import gift.domain.category.CategoryRequest;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> readCategories(
        @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
        @RequestParam(required = false, defaultValue = "10", value = "size") int pageSize) {
        return categoryService.findAll(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public Category readCategory(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category createCategory(
        @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @PatchMapping("/{id}")
    public Category updateCategory(@PathVariable("id") Long id,
        @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
