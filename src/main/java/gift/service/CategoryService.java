package gift.service;

import gift.common.exception.DuplicateDataException;
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

    private void checkDuplicateCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new DuplicateDataException("Category with name " + request.name() + " already exists", "Duplicate Category");
        }
    }
}
