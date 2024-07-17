package gift.Controller;

import gift.Model.Category;
import gift.Service.CategoryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/api/category")
    public ResponseEntity<List<Category>> getCategory(Model model){
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }
}
