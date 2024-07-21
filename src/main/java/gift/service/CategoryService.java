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
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = categories.stream()
            .map(cat -> CategoryResponse.fromEntity(cat))
            .toList();
        return responses;
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No such category"));
        return category;
    }

    @Transactional
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

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest newCategory) {
        Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No such category"));
        category.setName(newCategory.getName());
        category.setColor(newCategory.getColor());
        category.setImageUrl(newCategory.getImageUrl());
        category.setDescription(newCategory.getDescription());

        categoryRepository.save(category);

        CategoryResponse response = CategoryResponse.fromEntity(category);
        return response;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No such category"));
        categoryRepository.delete(category);
    }


}
