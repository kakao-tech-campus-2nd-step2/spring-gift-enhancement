package gift.category;

import gift.product.ProductDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id)
        throws NotFoundException {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result.getAllErrors().toString());
        }
        categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("카테고리가 추가되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long id,
        @Valid @RequestBody CategoryDTO categoryDTO, BindingResult result)
        throws NotFoundException {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result.getAllErrors().toString());
        }
        categoryDTO.setId(id);
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok("업데이트에 성공했습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id)
        throws NotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
