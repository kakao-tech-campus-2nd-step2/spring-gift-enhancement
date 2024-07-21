package gift.category.service;

import gift.category.dto.CategoryIdDto;
import gift.category.dto.CategoryRequestDto;
import gift.category.dto.CategoryResponseDto;
import gift.category.entity.Category;
import gift.category.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 카테고리를 추가하는 핸들러.
    public void insertCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category(categoryRequestDto.name(), categoryRequestDto.imageUrl());

        Category actualCategory = categoryRepository.save(category);
    }

    // resolver에서 사용할 하나만 조회하는 핸들러
    @Transactional
    public CategoryResponseDto selectCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category actualCategory = getActualCategory(optionalCategory);

        return new CategoryResponseDto(actualCategory.getCategoryId(), actualCategory.getName(),
            actualCategory.getImageUrl());
    }

    // 카테고리를 조회하는 핸들러.
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> selectCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(
            category -> new CategoryResponseDto(category.getCategoryId(), category.getName(),
                category.getImageUrl())).collect(Collectors.toList());
    }

    // 카테고리를 수정하는 핸들러.
    @Transactional
    public void updateCategory(CategoryIdDto categoryIdDto, CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryIdDto.categoryId();

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category actualCategory = getActualCategory(optionalCategory);

        actualCategory.updateCategory(categoryRequestDto.name(), categoryRequestDto.imageUrl());
    }

    // 카테고리를 삭제하는 핸들러.
    @Transactional
    public void deleteCategory(CategoryIdDto categoryIdDto) {
        Long categoryId = categoryIdDto.categoryId();
        verifyCategoryExists(categoryId);

        categoryRepository.deleteById(categoryId);
    }

    // id로 카테고리가 존재하는지 검증
    private void verifyCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }
    }

    // 검증을 마치고 가져오기
    private Category getActualCategory(Optional<Category> optionalCategory) {
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }

        return optionalCategory.get();
    }
}
