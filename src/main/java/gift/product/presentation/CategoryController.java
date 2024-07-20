package gift.product.presentation;

import gift.product.application.CategoryService;
import gift.product.domain.CreateCategoryRequest;
import gift.util.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok(new CommonResponse<>(
                categoryService.getCategory(),
                "카테고리 조회 성공",
                true
        ));
    }

    @PostMapping("/create") // TODO: 관리자만 접근 가능하도록 수정
    public ResponseEntity<?> addCategory(@RequestBody @Validated CreateCategoryRequest request) {
        categoryService.addCategory(request);
        return ResponseEntity.ok(new CommonResponse<>(
                null,
                "카테고리 추가 성공",
                true
        ));
    }
}
