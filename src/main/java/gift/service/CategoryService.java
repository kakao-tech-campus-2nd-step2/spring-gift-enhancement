package gift.service;

import gift.dto.CategoryRequest;
import gift.exception.category.CategoryNotFoundException;
import gift.model.Category;
import gift.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category update(CategoryRequest request) {
        Optional<Category> category = categoryRepository.findById(request.id());
        if (category.isPresent()) {
            category.get().updateCategory(
                    request.name(),
                    request.color(),
                    request.imageUrl(),
                    request.description()
            );
            return category.get();
        }
        throw new CategoryNotFoundException("해당 카테고리가 존재하지 않습니다.");
    }
}
