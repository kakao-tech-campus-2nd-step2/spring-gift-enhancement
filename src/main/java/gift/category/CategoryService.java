package gift.category;

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
            .orElseThrow(() -> new IllegalArgumentException("Category 가 잘못되었습니다."));
        return CategoryResponseDto.from(category);
    }

    @Transactional
    public Long insertCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.save(categoryRequestDto.toEntity());
        return category.getId();
    }
}
