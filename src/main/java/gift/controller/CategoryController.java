package gift.controller;

import gift.dto.CategoryDto;
import gift.dto.request.CategoryRequest;
import gift.dto.response.CategoryResponse;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> categoryList() {
        List<CategoryDto> categoryDtoList = categoryService.getCategories();
        List<CategoryResponse> categoryResponseList = categoryDtoList.stream()
                .map(CategoryDto::toResponseDto)
                .toList();

        return ResponseEntity.ok()
                .body(categoryResponseList);
    }

    @PostMapping
    public ResponseEntity<Void> categoryAdd(@RequestBody @Valid CategoryRequest request) {
        CategoryDto categoryDto = new CategoryDto(request.getName());

        categoryService.addCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> categoryEdit(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest request) {
        CategoryDto categoryDto = new CategoryDto(request.getName());
        categoryService.editCategory(categoryId, categoryDto);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> categoryRemove(@PathVariable Long categoryId) {
        categoryService.removeCategory(categoryId);

        return ResponseEntity.ok()
                .build();
    }

}
