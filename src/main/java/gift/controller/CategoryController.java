package gift.controller;

import gift.dto.CategoryRequestDTO;
import gift.dto.CategoryResponseDTO;
import gift.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategory () {
        List<CategoryResponseDTO> categoryList = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryList);
    }

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory (@RequestBody CategoryRequestDTO categoryRequestDTO) {
        categoryService.addCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category created");
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<String> updateCategory (@PathVariable Long CategoryId,
                                                  @RequestBody CategoryRequestDTO categoryRequestDTO) {
        categoryService.updateCategory(CategoryId, categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category updated");
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory (@PathVariable Long CategoryId) {
        categoryService.removeCategory(CategoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Category deleted");
    }

}