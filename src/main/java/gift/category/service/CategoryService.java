package gift.category.service;

import gift.category.model.CategoryRepository;
import gift.category.model.dto.Category;
import gift.category.model.dto.CategoryResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public CategoryResponse findCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndIsActiveTrue(id);
        return categoryOptional.map(CategoryResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Category"));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllCategories(Long id) {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }
}
