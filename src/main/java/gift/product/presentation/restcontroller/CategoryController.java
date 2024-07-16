package gift.product.presentation.restcontroller;

import gift.product.business.service.CategoryService;
import gift.product.presentation.dto.ResponseCategoryDto;
import java.util.List;
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
    public ResponseEntity<List<ResponseCategoryDto>> getCategories() {
        var categoryDtos = categoryService.getCategories();
        var responseCategoryDtos = ResponseCategoryDto.of(categoryDtos);
        return ResponseEntity.ok(responseCategoryDtos);
    }

}
