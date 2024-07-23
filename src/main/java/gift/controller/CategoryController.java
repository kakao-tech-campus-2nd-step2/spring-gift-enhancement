package gift.controller;

import gift.dto.CategoryRequest;
import gift.dto.ResponseMessage;
import gift.entity.Category;
import gift.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryRequest request) {
        Category category = categoryService.addCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping
    public Page<Category> getCategory(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public Category getOneCategory(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
        @RequestBody CategoryRequest request) {
        Category category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        ResponseMessage responseMessage = new ResponseMessage("삭제되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
