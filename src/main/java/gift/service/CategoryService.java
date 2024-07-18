package gift.service;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category(name);
            categoryRepository.save(category);
        }
        return category;
    }
}
