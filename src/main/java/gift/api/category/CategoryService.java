package gift.api.category;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(Pageable pageable) {
        Page<Category> allCategories = categoryRepository.findAll(pageable);
        return allCategories.hasContent() ? allCategories.getContent() : Collections.emptyList();
    }
}
