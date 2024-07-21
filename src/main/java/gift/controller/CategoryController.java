package gift.controller;

import gift.dto.CategoryResponseDto;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryResponseDto getAllCategories() {
        CategoryResponseDto categoryResponseDto = categoryService.getDtoOfAllCategories();
        categoryResponseDto.setHttpStatus(HttpStatus.OK);
        return categoryResponseDto;
    }

    @GetMapping("/{product_id}")
    public CategoryResponseDto requestDtoOfGetCategoryOfProduct(@RequestParam("product_id") Long productId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryDtoByProductId(productId);
        categoryResponseDto.setHttpStatus(HttpStatus.OK);
        return categoryResponseDto;
    }
}
