package gift.product.service;

import gift.product.domain.Category;
import gift.product.persistence.CategoryRepository;
import gift.product.service.dto.CategoryParam;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long createCategory(CategoryParam categoryParam) {
        Category category = CategoryParam.toEntity(categoryParam);
        category = categoryRepository.save(category);
        return category.getId();
    }
}
