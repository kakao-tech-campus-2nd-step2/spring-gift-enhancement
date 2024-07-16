package gift.controller;

import gift.dto.common.apiResponse.ApiResponseBody.SuccessBody;
import gift.dto.common.apiResponse.ApiResponseGenerator;
import gift.dto.requestDTO.CategoryRequestDTO;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<SuccessBody<Long>> addProduct(
        @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        Long categoryId = categoryService.addCategory(categoryRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.CREATED, "카테고리가 생성되었습니다.", categoryId);
    }

}
