package gift.category.service;

import gift.category.domain.Category;
import gift.category.dto.CategoryServiceDto;
import gift.category.exception.CategoryNotFoundException;
import gift.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Category createCategory(CategoryServiceDto categoryServiceDto) {
        return categoryRepository.save(categoryServiceDto.toCategory());
    }

    public Category updateCategory(CategoryServiceDto categoryServiceDto) {
        validateCategoryExists(categoryServiceDto.id());
        return categoryRepository.save(categoryServiceDto.toCategory());
    }

    public void deleteCategory(Long id) {
        validateCategoryExists(id);
        categoryRepository.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

}
