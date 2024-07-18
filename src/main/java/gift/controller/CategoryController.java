package gift.controller;

import gift.classes.RequestState.CategoryRequestStateDTO;
import gift.classes.RequestState.RequestStateDTO;
import gift.classes.RequestState.RequestStatus;
import gift.dto.CategoryDto;
import gift.dto.RequestCategoryDto;
import gift.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //    Categories 조회
    @GetMapping
    public ResponseEntity<CategoryRequestStateDTO> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();

        return ResponseEntity.ok().body(new CategoryRequestStateDTO(
            RequestStatus.success,
            null,
            categories
        ));
    }

    //    Category 추가
    @PostMapping
    public ResponseEntity<RequestStateDTO> addCategory(
        @RequestBody RequestCategoryDto requestCategoryDto) {
        categoryService.addCategory(requestCategoryDto);
        return ResponseEntity.ok().body(new RequestStateDTO(
            RequestStatus.success,
            null
        ));
    }

    //    Category 삭제
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
