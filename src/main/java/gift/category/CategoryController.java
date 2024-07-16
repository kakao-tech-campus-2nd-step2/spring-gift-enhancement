package gift.category;

import gift.common.model.PageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<PageResponseDto<CategoryResponseDto>> getAllCategories(
        @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok()
            .body(PageResponseDto.of(categoryService.getAllCategories(pageable), pageable));
    }

}
