package gift.controller;

import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.entity.Category;
import gift.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponseDto> categoriesDto = categories.stream()
            .map(cate -> new CategoryResponseDto(
                cate.getId(),
                cate.getName()
            ))
            .collect(Collectors.toList());
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);

    }

}
