package gift.controller;

import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.entity.Category;
import gift.exception.CategoryException;
import gift.service.CategoryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponseDto> categoriesDto = categories.stream()
            .map(cate -> new CategoryResponseDto(
                cate.getId(),
                cate.getName()
            ))
            .collect(Collectors.toList());
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        if(categoryService.existsByName(categoryRequestDto.getName())) {
            throw new CategoryException("이미 존재하는 카테고리입니다.");
        }
        Category category = categoryRequestDto.toEntity();
        categoryService.save(category);
        return new ResponseEntity<>(new CategoryResponseDto(category.getId(), category.getName()), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto ){
        Optional<Category> categoryOP = categoryService.findById(id);
        if(categoryOP.isEmpty()){
            throw new CategoryException("존재하지 않는 카테고리 입니다.");
        }
        Category category = categoryOP.get();
        category.setName(categoryRequestDto.getName());
        categoryService.save(category);
        return new ResponseEntity<>(new CategoryResponseDto(category.getId(), category.getName()), HttpStatus.OK);
    }

}
