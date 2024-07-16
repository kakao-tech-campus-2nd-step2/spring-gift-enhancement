package gift.service;

import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import gift.model.Category;

import java.util.List;
import java.util.NoSuchElementException;

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
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
}
