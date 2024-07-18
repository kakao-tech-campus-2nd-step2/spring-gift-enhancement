package gift.domain.service;

import gift.domain.dto.request.CategoryRequest;
import gift.domain.dto.response.CategoryResponse;
import gift.domain.entity.Category;
import gift.domain.exception.CategoryAlreadyExistsException;
import gift.domain.exception.CategoryNotFoundException;
import gift.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    public CategoryResponse addCategory(CategoryRequest request) {
        categoryRepository.findByName(request.name()).ifPresent(c -> {
            throw new CategoryAlreadyExistsException();
        });

        return CategoryResponse.of(categoryRepository.save(request.toEntity()));
    }
}
