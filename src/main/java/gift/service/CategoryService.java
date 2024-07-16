package gift.service;

import gift.domain.Category;
import gift.domain.CategoryType;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String categoryType) {
        Category category = categoryRepository.findByName(categoryType);
        if (category == null) {
            category = new Category(CategoryType.valueOf(categoryType));
            categoryRepository.save(category);
        }
        return category;
    }
}
