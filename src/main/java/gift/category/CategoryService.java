package gift.category;

import static gift.exception.ErrorMessage.CATEGORY_NOT_FOUND;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(Category category) {
        return categoryRepository.findByName(category.getName())
            .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));
    }
}
