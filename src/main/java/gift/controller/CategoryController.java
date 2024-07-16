package gift.controller;

import gift.model.Category;
import gift.model.CategoryDto;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
    Category category = categoryService.convertToEntity(categoryDto);
    Category savedCategory = categoryService.saveCategory(category);
    return new ResponseEntity<>(categoryService.convertToDto(savedCategory), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<Category> categories = categoryService.findAll();
    List<CategoryDto> categoryDtos = categories.stream()
            .map(categoryService::convertToDto)
            .collect(Collectors.toList());
    return ResponseEntity.ok(categoryDtos);
  }
}