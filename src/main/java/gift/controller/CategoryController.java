package gift.controller;

import gift.model.CategoryDTO;
import gift.model.CategoryPageDTO;
import gift.model.ProductDTO;
import gift.service.CategoryService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getCategoryPage(Pageable pageable) {
        CategoryPageDTO categoryPage = categoryService.findCategoryPage(pageable.getPageNumber(),
            pageable.getPageSize());
        return ResponseEntity.ok().body(categoryPage.categories());
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<?> getCategory(@RequestParam(required = true) long categoryId) {
        CategoryDTO categoryDTO = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> getProductsInCategory(Pageable pageable,
        @PathVariable long categoryId) {
        List<ProductDTO> products = categoryService.findProductsInCategory(categoryId);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        return ResponseEntity.ok(products.subList(start, end));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable long categoryId,
        @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}