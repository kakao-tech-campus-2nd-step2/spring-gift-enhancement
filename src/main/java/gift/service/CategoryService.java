package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.CategoryRequest;
import gift.controller.dto.response.CategoryResponse;
import gift.model.Category;
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

    @Transactional
    public Long save(CategoryRequest request) {
        checkDuplicateCategory(request);
        Category category = new Category(request.name(), request.color(), request.imageUrl(), request.description());
        return categoryRepository.save(category).getId();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional
    public void updateById(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with id " + id + " not found"));
        category.updateCategory(request.name(), request.color(), request.imageUrl(), request.description());
    }

    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    private void checkDuplicateCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new DuplicateDataException("Category with name " + request.name() + " already exists", "Duplicate Category");
        }
    }
}
