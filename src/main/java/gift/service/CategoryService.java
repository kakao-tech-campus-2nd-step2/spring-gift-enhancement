package gift.service;

import gift.model.category.Category;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.repository.category.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponse::from)
            .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return CategoryResponse.from(category);
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequest.toEntity(categoryRequest.name(), categoryRequest.color(),
            categoryRequest.imageUrl(), categoryRequest.description());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.update(categoryRequest);
        return CategoryResponse.from(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }






}
