package gift.controller;

import gift.controller.dto.CategoryRequestDTO;
import gift.controller.dto.CategoryResponseDTO;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<CategoryResponseDTO>> getCategory(){
        List<CategoryResponseDTO> allCategory = categoryService.findAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO
        categoryRequestDTO){
        CategoryResponseDTO category = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestParam Long id, @Valid
    @RequestBody CategoryRequestDTO categoryRequestDTO){
        categoryService.updateCategory(id,categoryRequestDTO);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCategory(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(id);
    }
}
