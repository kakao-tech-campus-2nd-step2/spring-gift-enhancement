package gift.main.controller;

import gift.main.entity.Category;
import gift.main.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategorylist() {
        List<Category> categoryList = categoryRepository.findAll();
        System.out.println("categoryList = " + categoryList);
        return ResponseEntity.ok(categoryList);
    }

}
