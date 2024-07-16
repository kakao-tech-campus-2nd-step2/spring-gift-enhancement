package gift.controller;

import gift.constants.SuccessMessage;
import gift.dto.CategoryDto;
import gift.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * 카테고리 추가 엔드포인트
     */
    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(SuccessMessage.ADD_CATEGORY_SUCCESS_MSG);
    }

    /**
     * 카테고리 수정 엔드포인트
     */
    @PutMapping
    public ResponseEntity<String> editCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.editCategory(categoryDto);
        return ResponseEntity.ok(SuccessMessage.EDIT_CATEGORY_SUCCESS_MSG);
    }

    /**
     * 카테고리 삭제 엔드포인트
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(SuccessMessage.DELETE_CATEGORY_SUCCESS_MSG);
    }

}
