package gift.controller;

import gift.dto.request.CategoryRequest;
import gift.repository.CategoryRepository;
import gift.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
