package gift.category;

import gift.category.model.Category;
import gift.category.model.CategoryRequestDto;
import gift.category.model.CategoryResponseDto;
import gift.common.exception.CategoryException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
            .map(CategoryResponseDto::from)
            .getContent();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        return CategoryResponseDto.from(category);
    }

    @Transactional
    public Long insertCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.save(categoryRequestDto.toEntity());
        return category.getId();
    }

    @Transactional
    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        category.updateInfo(categoryRequestDto.toEntity());
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
