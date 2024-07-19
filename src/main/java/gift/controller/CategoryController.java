package gift.controller;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.exception.customException.CustomDuplicateException;
import gift.model.categories.CategoryDTO;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryForm,
        BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        if (categoryService.isDuplicateName(categoryForm.getName())) {
            result.rejectValue("name", "", ErrorCode.DUPLICATE_NAME.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        return ResponseEntity.ok(categoryService.insertCategory(categoryForm));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id,
        @Valid @RequestBody CategoryDTO categoryForm, BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        if (categoryService.isDuplicateName(categoryForm.getName())) {
            result.rejectValue("name", "", ErrorCode.DUPLICATE_NAME.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        CategoryDTO updated = categoryService.updateCategory(
            new CategoryDTO(id, categoryForm.getName(), categoryForm.getImgUrl()));
        return ResponseEntity.ok(updated);
    }
}
