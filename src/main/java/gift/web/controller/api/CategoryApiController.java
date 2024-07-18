package gift.web.controller.api;

import gift.service.CategoryService;
import gift.web.dto.request.category.CreateCategoryRequest;
import gift.web.dto.response.category.CreateCategoryResponse;
import gift.web.dto.response.category.ReadAllCategoriesResponse;
import gift.web.dto.response.category.ReadCategoryResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(@Validated @RequestBody CreateCategoryRequest request)
        throws URISyntaxException {
        CreateCategoryResponse response = categoryService.createCategory(request);

        URI location = new URI("http://localhost:8080/api/categories/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<ReadAllCategoriesResponse> readAllCategories() {
        ReadAllCategoriesResponse response = categoryService.readAllCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCategoryResponse> readCategory(@PathVariable Long id) {
        ReadCategoryResponse response = categoryService.readCategory(id);
        return ResponseEntity.ok(response);
    }

}
