package gift.controller;

import gift.dto.CategoryDto;
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
  public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
    CategoryDto savedCategoryDto = categoryService.saveCategory(categoryDto);
    return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
  }
  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<CategoryDto> categories = categoryService.findAllCategories();
    return ResponseEntity.ok(categories);
  }
}