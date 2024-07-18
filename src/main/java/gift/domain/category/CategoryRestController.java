package gift.domain.category;

import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ResultResponseDto<List<Category>>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseMaker.createResponse(HttpStatus.OK, "전체 카테코리 목록 조회 성공", categories);
    }

    @PostMapping
    public ResponseEntity<SimpleResultResponseDto> createCategory(
        @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "카테고리 추가 성공");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SimpleResultResponseDto> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "카테고리 삭제 성공");
    }

    @PutMapping("{id}")
    public ResponseEntity<SimpleResultResponseDto> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "카테고리 수정 성공");
    }
}
