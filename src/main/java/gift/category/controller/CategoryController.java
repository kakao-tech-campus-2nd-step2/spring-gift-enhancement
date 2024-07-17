package gift.category.controller;

import gift.category.domain.CategoryDTO;
import gift.category.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoryDTO>> findById(@PathVariable("id") Long CategoryId){
        return new ResponseEntity<>(categoryService.findById(CategoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.update(categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CategoryDTO> deleteCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
