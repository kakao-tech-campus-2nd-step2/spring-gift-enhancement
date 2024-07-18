package gift.domain.controller;

import gift.domain.controller.apiResponse.CategoryAddApiResponse;
import gift.domain.controller.apiResponse.CategoryGetApiResponse;
import gift.domain.controller.apiResponse.CategoryListApiResponse;
import gift.domain.dto.request.CategoryRequest;
import gift.domain.dto.response.CategoryResponse;
import gift.domain.service.CategoryService;
import gift.global.apiResponse.SuccessApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping
    public ResponseEntity<CategoryAddApiResponse> addCategory(@RequestBody CategoryRequest request) {
        var result = categoryService.addCategory(request);
        return SuccessApiResponse.created(
            new CategoryAddApiResponse(HttpStatus.CREATED, result),
            "/api/categories/{id}",
            result.id());
    }

    @GetMapping
    public ResponseEntity<CategoryListApiResponse> getCategoryList() {
        return SuccessApiResponse.ok(new CategoryListApiResponse(HttpStatus.OK, categoryService.getCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryGetApiResponse> getCategory(@PathVariable("id") Long id) {
        return SuccessApiResponse.ok(
            new CategoryGetApiResponse(HttpStatus.OK, CategoryResponse.of(categoryService.findById(id))));
    }
}
