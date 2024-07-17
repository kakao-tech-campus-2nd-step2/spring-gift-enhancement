package gift.product.application;

import gift.auth.Authorized;
import gift.member.domain.Role;
import gift.product.application.dto.request.CategoryRequest;
import gift.product.application.dto.response.CategoryResponse;
import gift.product.service.CategoryService;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @Authorized(Role.USER)
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest request) {
        var savedCategoryId = categoryService.createCategory(request.toCategoryParam());

        return ResponseEntity.created(URI.create("/api/categories/" + savedCategoryId))
                .build();
    }

    @PutMapping("/{id}")
    @Authorized(Role.USER)
    @ResponseStatus(HttpStatus.OK)
    public void modifyCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        categoryService.modifyCategory(id, request.toCategoryParam());
    }

    @GetMapping("/{id}")
    @Authorized(Role.USER)
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        var categoryInfo = categoryService.getCategoryInfo(id);

        var response = CategoryResponse.from(categoryInfo);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping()
    @Authorized(Role.USER)
    public ResponseEntity<List<CategoryResponse>> getCategoryList() {
        var categoryList = categoryService.getCategoryList();

        var response = categoryList.stream()
                .map(CategoryResponse::from)
                .toList();
        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{id}")
    @Authorized(Role.USER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
