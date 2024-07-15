package gift.category.controller;

import gift.category.dto.CategoryReqDto;
import gift.category.dto.CategoryResDto;
import gift.category.service.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories() {
        List<CategoryResDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryReqDto categoryReqDto) {
        categoryService.addCategory(categoryReqDto);
        return ResponseEntity.created(URI.create("/api/categories")).body("카테고리를 추가했습니다.");
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable("category-id") Long categoryId,
            @Valid @RequestBody CategoryReqDto categoryReqDto
    ) {
        categoryService.updateCategory(categoryId, categoryReqDto);
        return ResponseEntity.ok("카테고리 정보를 수정했습니다.");
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category-id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("카테고리를 삭제했습니다.");
    }
}
