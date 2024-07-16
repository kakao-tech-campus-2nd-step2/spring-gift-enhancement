package gift.category.application;

import gift.category.dao.CategoryRepository;
import gift.category.dto.CategoryRequest;
import gift.category.dto.CategoryResponse;
import gift.category.entity.Category;
import gift.category.util.CategoryMapper;
import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    public CategoryResponse getCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toResponseDto)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        return CategoryMapper.toResponseDto(
                categoryRepository.save(CategoryMapper.toEntity(request))
        );
    }

    public Long deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
        return id;
    }

    public Long updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        category.update(request);

        return categoryRepository.save(category)
                                 .getId();
    }

}
