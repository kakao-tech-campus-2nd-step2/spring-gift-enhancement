package gift.service;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import gift.request.CategoryRequest;
import gift.response.CategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();
    }

    public void addCategory(CategoryRequest request) {
        Category category = new Category(request.getName(), request.getColor(), request.getImageUrl(), request.getDescription());

        categoryRepository.save(category);
    }

}
