package gift.service;

import gift.domain.Category;
import gift.domain.CategoryName;
import gift.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Category not found"));
    }

    public Category getCategoryByName(CategoryName name) {
        return categoryRepository.findByName(name).orElseThrow(()
            -> new IllegalArgumentException("Category not found"));
    }
}
