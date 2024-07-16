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

    public Category addCategory(CategoryDTO categoryDTO) {
        return categoryRepository.save(categoryDTO.toEntity());
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(CategoryDTO categoryDTO) {
        return categoryRepository.findByName(categoryDTO.getName())
            .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));
    }
}
