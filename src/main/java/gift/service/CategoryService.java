package gift.service;

import gift.model.category.Category;
import gift.model.category.CategoryRequest;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequest.toEntity();
        categoryRepository.save(category);
    }
}
