package gift.service;

import gift.domain.Category;
import gift.dto.CategoryDto;
import gift.exception.CategoryNameNotDuplicateException;
import gift.exception.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();
    }

    public void addCategory(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new CategoryNameNotDuplicateException();
        }

        Category category = new Category(dto.getName());

        categoryRepository.save(category);
    }

    public void editCategory(Long categoryId, CategoryDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        if (!category.getName().equals(dto.getName()) && categoryRepository.existsByName(dto.getName())) {
            throw new CategoryNameNotDuplicateException();
        }

        category.changeName(dto.getName());
    }

    public void removeCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
