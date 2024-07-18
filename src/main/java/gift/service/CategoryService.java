package gift.service;

import gift.domain.category.Category;
import gift.domain.category.CategoryRepository;
import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryRequestDto request) {
        categoryRepository.save(new Category(request.name(), request.color(), request.description(), request.imageUrl()));
    }

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll().stream().map(CategoryResponseDto::new).toList();
    }

    public CategoryResponseDto getSingleCategory(long id) {
        Category category = getCategory(id);
        return new CategoryResponseDto(category);
    }

    public CategoryResponseDto editCategory(long id, CategoryRequestDto request) {
        Category category = getCategory(id);
        category.update(request.name(), request.color(), request.description(), request.imageUrl());
        categoryRepository.save(category);
        return new CategoryResponseDto(category);
    }

    public void deleteCategory(long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }

    private Category getCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CATEGORY, id));
    }
}
