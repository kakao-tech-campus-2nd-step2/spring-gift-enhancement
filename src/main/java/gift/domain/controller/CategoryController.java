package gift.domain.controller;

import gift.domain.controller.apiResponse.CategoryAddApiResponse;
import gift.domain.dto.request.CategoryRequest;
import gift.domain.service.CategoryService;
import gift.global.apiResponse.SuccessApiResponse;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<CategoryAddApiResponse> addCategory(@RequestBody CategoryRequest request) {
        var result = categoryService.addCategory(request);
        return SuccessApiResponse.created(
            new CategoryAddApiResponse(HttpStatus.CREATED, result),
            "/api/categories/{id}",
            result.id());
    }
}
