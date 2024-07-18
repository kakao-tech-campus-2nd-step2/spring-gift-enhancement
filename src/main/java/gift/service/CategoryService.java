package gift.service;

import gift.DTO.category.CategoryRequest;
import gift.DTO.category.CategoryResponse;
import gift.domain.Category;
import gift.exception.category.DuplicateCategoryNameException;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = categories.stream()
            .map(cat -> CategoryResponse.fromEntity(cat))
            .toList();
        return responses;
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.orElseThrow(() -> new RuntimeException("No such category"));
        return category.get();
    }

    public CategoryResponse addCategory(CategoryRequest newCategory) {
        categoryRepository.findByName(newCategory.getName())
            .ifPresent(cat -> {
                throw new DuplicateCategoryNameException();
            });
        Category category = newCategory.toEntity();
        categoryRepository.save(category);

        CategoryResponse response = CategoryResponse.fromEntity(category);
        return response;
    }
}
