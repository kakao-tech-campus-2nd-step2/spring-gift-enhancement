package gift.service;

import gift.domain.model.dto.CategoryAddRequestDto;
import gift.domain.model.dto.CategoryResponseDto;
import gift.domain.model.entity.Category;
import gift.domain.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponseDto::toDto)
            .collect(Collectors.toList());
    }

    public CategoryResponseDto addCategory(CategoryAddRequestDto categoryAddRequestDto) {
        validateCategoryName(categoryAddRequestDto.getName());

        Category category = categoryAddRequestDto.toEntity();
        Category savedCategory = categoryRepository.save(category);

        return CategoryResponseDto.toDto(savedCategory);
    }

    private void validateCategoryName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }
    }
}
