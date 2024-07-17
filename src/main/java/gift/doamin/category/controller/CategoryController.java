package gift.doamin.category.controller;

import gift.doamin.category.dto.CategoryForm;
import gift.doamin.category.dto.CategoryParam;
import gift.doamin.category.service.CategoryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createNewCategory(@RequestBody CategoryForm categoryForm) {
        categoryService.createCategory(categoryForm);
    }

    @GetMapping
    public List<CategoryParam> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryParam getOneCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}
