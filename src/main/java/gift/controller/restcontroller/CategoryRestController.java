package gift.controller.restcontroller;

import gift.controller.dto.request.CategoryRequest;
import gift.controller.dto.response.CategoryResponse;
import gift.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/category")
    @Operation(summary = "카테고리 저장", description = "카테고리를 저장합니다.")
    public ResponseEntity<Long> createCategory(@Valid @RequestBody CategoryRequest request) {
        Long id = categoryService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/categories")
    @Operation(summary = "전체 카테고리 조회", description = "전체 카테고리를 조회합니다.")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> responses = categoryService.getAllCategories();
        return ResponseEntity.ok().body(responses);
    }

}
