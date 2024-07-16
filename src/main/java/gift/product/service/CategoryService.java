package gift.product.service;

import gift.product.dto.CategoryDto;
import gift.product.model.Category;
import gift.product.repository.CategoryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryAll() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return getValidatedCategory(id);
    }

    @Transactional
    public Category insertCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.name());

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        getValidatedCategory(id);

        Category category = new Category(id, categoryDto.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        getValidatedCategory(id);
        categoryRepository.deleteById(id);
    }

    private Category getValidatedCategory(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 ID의 카테고리가 존재하지 않습니다."));
    }
}
