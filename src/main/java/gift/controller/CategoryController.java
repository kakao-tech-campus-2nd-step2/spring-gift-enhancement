package gift.controller;

import gift.service.CategoryService;
import gift.vo.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = service.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
