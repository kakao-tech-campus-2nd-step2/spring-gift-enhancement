package gift.controller.category;

import gift.config.LoginMember;
import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.service.CategoryService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(product));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@LoginMember LoginResponse loginMember,
        @PathVariable UUID categoryId, @RequestBody CategoryRequest category) {
        AuthController.validateAdmin(loginMember);
        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.update(categoryId, category));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@LoginMember LoginResponse loginMember,
        @PathVariable UUID categoryId) {
        AuthController.validateAdmin(loginMember);
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}