package gift.service;

import gift.dto.request.AddCategoryRequest;
import gift.dto.request.UpdateCategoryRequest;
import gift.dto.response.CategoryIdResponse;
import gift.dto.response.CategoryResponse;
import gift.entity.Category;
import gift.exception.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().
                stream().
                map(CategoryResponse::fromCategory)
                .toList();
    }

    public CategoryIdResponse addCategory(AddCategoryRequest request) {
        Category category = new Category(request.name(), request.color(), request.imageUrl(), request.description());
        return new CategoryIdResponse(categoryRepository.save(category).getId());
    }

    @Transactional
    public void updateCategory(UpdateCategoryRequest request) {
        checkCategoryExistence(request.id())
                .update(request);
    }

    public Category getCategory(Long categoryId) {
        return checkCategoryExistence(categoryId);
    }

    public void deleteCategory(Long categoryId) {
        checkCategoryExistence(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    private Category checkCategoryExistence(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
