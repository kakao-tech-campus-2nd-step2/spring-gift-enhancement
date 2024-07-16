package gift.controller;

import gift.dto.common.apiResponse.ApiResponseBody.SuccessBody;
import gift.dto.common.apiResponse.ApiResponseGenerator;
import gift.dto.requestDTO.CategoryRequestDTO;
import gift.dto.responseDTO.CategoryListResponseDTO;
import gift.dto.responseDTO.CategoryResponseDTO;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/category/{id}")
    public ResponseEntity<SuccessBody<CategoryResponseDTO>> getOneCategory(
        @PathVariable("id") Long categoryId) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getOneCategory(categoryId);
        return ApiResponseGenerator.success(HttpStatus.OK, "id : " + categoryId + " 카테고리를 조회했습니다.",
            categoryResponseDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<SuccessBody<CategoryListResponseDTO>> getAllCategory() {
        CategoryListResponseDTO categoryListResponseDTO = categoryService.getAllCategories();
        return ApiResponseGenerator.success(HttpStatus.OK, "모든 카테고리를 조회했습니다.",
            categoryListResponseDTO);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<SuccessBody<Long>> updateCategory(
        @PathVariable("id") Long categoryId,
        @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {

        Long updatedCategoryId = categoryService.updateCategory(categoryId, categoryRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK, "카테고리가 수정되었습니다.", updatedCategoryId);
    }
}
