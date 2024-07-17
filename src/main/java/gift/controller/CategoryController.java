package gift.controller;

import gift.domain.model.dto.CategoryAddRequestDto;
import gift.domain.model.dto.CategoryResponseDto;
import gift.domain.model.dto.CategoryUpdateRequestDto;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto addCategory(
        @Valid @RequestBody CategoryAddRequestDto categoryAddRequestDto) {
        return categoryService.addCategory(categoryAddRequestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(
        @Valid @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return categoryService.updateCategory(categoryUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
