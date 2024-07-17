package gift.service;

import gift.domain.Category;
import gift.dto.request.CategoryRequestDto;
import gift.dto.response.CategoryResponseDto;
import gift.exception.CategoryNameDuplicationException;
import gift.exception.EntityNotFoundException;
import gift.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> findAllCategories(){
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto findOneCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 카테고리는 존재하지 않습니다."));

        return CategoryResponseDto.from(category);
    }

    @Transactional
    public CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDto){
        categoryRepository.findCategoryByName(categoryRequestDto.name())
                .ifPresent(e -> {
                    throw new CategoryNameDuplicationException();
                });

        Category category = new Category(categoryRequestDto.name(), categoryRequestDto.color());

        categoryRepository.save(category);

        return CategoryResponseDto.from(category);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 카테고리는 존재하지 않습니다."));

        categoryRepository.findCategoryByName(categoryRequestDto.name())
                .ifPresent(e -> {
                    throw new CategoryNameDuplicationException();
                });

        category.update(categoryRequestDto);

        return CategoryResponseDto.from(category);
    }

    @Transactional
    public CategoryResponseDto deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 카테고리는 존재하지 않습니다."));

        categoryRepository.deleteById(categoryId);

        return CategoryResponseDto.from(category);
    }
}
