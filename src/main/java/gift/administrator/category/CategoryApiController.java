package gift.administrator.category;

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
@RequestMapping(value = "/api/categories", produces = "application/json;UTF-8")
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
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult bindingResult) {
        categoryService.existsByNamePutResult(categoryDTO.getName(), bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(bindingResult.getAllErrors().toString());
        }
        CategoryDTO result = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,
        @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult)
        throws NotFoundException {
        categoryDTO.setId(id);
        categoryService.existsByNameAndIdPutResult(categoryDTO.getName(), categoryDTO.getId(), bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(bindingResult.getAllErrors().toString());
        }
        CategoryDTO result = categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") Long id)
        throws NotFoundException {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
