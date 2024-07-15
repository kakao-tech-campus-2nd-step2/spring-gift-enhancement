package gift.service;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import gift.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();
    }

}
