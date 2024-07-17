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
        checkDuplicatedCategoryName(categoryParam.name());

        Category category = CategoryParam.toEntity(categoryParam);
        category = categoryRepository.save(category);
        return category.getId();
    }

    public void modifyCategory(Long categoryId, CategoryParam categoryParam) {
        checkDuplicatedCategoryName(categoryParam.name());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.modify(categoryParam.name(), categoryParam.color(), categoryParam.imgUrl(),
                categoryParam.description());
    }

    private void checkDuplicatedCategoryName(String name) {
        categoryRepository.findByName(name).ifPresent(category -> {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        });
    }
}
