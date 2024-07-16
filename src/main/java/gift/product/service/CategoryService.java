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
        categoryRepository.findByName(categoryParam.name()).ifPresent(category -> {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        });
        
        Category category = CategoryParam.toEntity(categoryParam);
        category = categoryRepository.save(category);
        return category.getId();
    }
}
