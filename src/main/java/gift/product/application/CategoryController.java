package gift.product.application;

import gift.product.application.dto.request.CategoryRequest;
import gift.product.service.CategoryService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping()
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest request) {
        var savedCategoryId = categoryService.createCategory(request.toCategoryParam());

        return ResponseEntity.created(URI.create("/api/categories/" + savedCategoryId))
                .build();
    }
}
