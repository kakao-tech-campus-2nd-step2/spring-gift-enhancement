package gift.controller;

import gift.common.dto.PageResponse;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> registerCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.register(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategory(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<CategoryResponse> response = categoryService.findAllCategory(pageable);
        return ResponseEntity.ok().body(response);
    }
}
