package gift.domain.product.service;

import gift.domain.product.dto.CategoryResponseDto;
import gift.domain.product.repository.CategoryJpaRepository;
import gift.domain.product.entity.Category;
import gift.exception.InvalidCategoryInfoException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryService(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    public List<CategoryResponseDto> readAll() {
        List<Category> categories = categoryJpaRepository.findAll();
        return categories.stream().map(CategoryResponseDto::from).toList();
    }

    public Category readById(long categoryId) {
        return categoryJpaRepository.findById(categoryId)
            .orElseThrow(() -> new InvalidCategoryInfoException("error.invalid.category.id"));
    }
}
