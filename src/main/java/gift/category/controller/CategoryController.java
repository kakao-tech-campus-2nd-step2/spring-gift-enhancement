package gift.category.controller;

import gift.category.model.Category;
import gift.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 1. 카테고리 생성
    @PostMapping
    public void createCategory(Category category) {
        categoryService.createCategory(category);
    }

    // 2. 카테고리 수정
    @PostMapping("/update/{id}")
    public void updateCategory(Long id, Category category) {
        categoryService.updateCategory(id, category);
    }

    // 3. 카테고리 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(Long id) {
        categoryService.deleteCategory(id);
    }

    // 4. 카테고리 조회
    @GetMapping()
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
