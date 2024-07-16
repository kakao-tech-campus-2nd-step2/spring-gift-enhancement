package gift.controller;

import gift.config.PageConfig;
import gift.dto.category.CategoryResponse;
import gift.dto.category.CreateCategoryRequest;
import gift.dto.category.UpdateCategoryRequest;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(
        @PageableDefault(size = PageConfig.PAGE_PER_COUNT, sort = PageConfig.SORT_STANDARD, direction = Direction.DESC) Pageable pageable) {
        Page<CategoryResponse> categoryPage = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categoryPage);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        Long categoryId = categoryService.createCategory(request);
        URI location = UriComponentsBuilder.newInstance()
            .path("/api/categories/{id}")
            .buildAndExpand(categoryId)
            .toUri();
        return ResponseEntity.created(location)
            .body(categoryId);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id,
        @RequestBody @Valid UpdateCategoryRequest request) {
        categoryService.updateCategory(id, request);
        return ResponseEntity.ok().build();
    }
}
