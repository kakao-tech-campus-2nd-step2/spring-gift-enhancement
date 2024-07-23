package gift.controller;

import gift.dto.ApiResponse;
import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody CategoryRequestDto request) {
        categoryService.saveCategory(request);
        return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK, "카테고리가 성공적으로 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<CategoryResponseDto> result = categoryService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable("id") Long id) {
        CategoryResponseDto result = categoryService.getSingleCategory(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> editCategory(@PathVariable("id") Long id,
                                                            @RequestBody CategoryRequestDto request) {
        CategoryResponseDto result = categoryService.editCategory(id, request);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(new ApiResponse(HttpStatus.NO_CONTENT, "성공적으로 삭제되었습니다."));
    }
}
