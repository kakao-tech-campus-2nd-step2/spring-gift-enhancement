package gift.controller;

import gift.dto.CategoryRequestDTO;
import gift.entity.Category;
import gift.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory () {
        List<Category> categoryList = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryList);
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory (@RequestBody CategoryRequestDTO categoryRequestDTO) {
        categoryService.addCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category created");
    }



}
