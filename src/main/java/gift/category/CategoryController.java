package gift.category;

import gift.category.model.CategoryRequest;
import gift.category.model.CategoryResponse;
import gift.common.model.PageResponseDto;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<PageResponseDto<CategoryResponse>> getAllCategories(
        @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
            PageResponseDto.of(categoryService.getAllCategories(pageable), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addCategory(
        @Valid @RequestBody CategoryRequest categoryRequest) {
        Long id = categoryService.insertCategory(categoryRequest);
        return ResponseEntity.created(URI.create("/api/categories/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
        @Valid @RequestBody CategoryRequest categoryRequest,
        @PathVariable(name = "id") Long id) {
        categoryService.updateCategory(categoryRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
