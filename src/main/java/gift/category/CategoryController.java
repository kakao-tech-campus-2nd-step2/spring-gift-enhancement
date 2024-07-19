package gift.category;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping()
    public ResponseEntity<Category> addCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.updateCategory(categoryRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteCategory(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
