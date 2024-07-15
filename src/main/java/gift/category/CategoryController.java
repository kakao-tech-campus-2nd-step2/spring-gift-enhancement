package gift.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDTO addCategories(@RequestBody CategoryRequestDTO newCategory){
        return categoryService.insertNewCategory(newCategory);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoriesByID(@PathVariable Long id){
        return categoryService.findCategoriesByID(id);
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategories(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryDTO){
        return categoryService.updateCategoriesByID(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategories(@PathVariable Long id){
        categoryService.deleteCategoriesByID(id);

    }


}
