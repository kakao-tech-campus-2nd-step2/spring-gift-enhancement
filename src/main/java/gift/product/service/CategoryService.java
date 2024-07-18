package gift.product.service;

import gift.product.domain.Category;
import gift.product.persistence.CategoryRepository;
import gift.product.service.command.CategoryCommand;
import gift.product.service.dto.CategoryInfo;
import gift.product.service.dto.CategoryPageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long createCategory(CategoryCommand categoryCommand) {
        checkDuplicatedCategoryName(categoryCommand.name());

        Category category = categoryCommand.toEntity();
        category = categoryRepository.save(category);
        return category.getId();
    }

    public void modifyCategory(Long categoryId, CategoryCommand categoryCommand) {
        checkDuplicatedCategoryName(categoryCommand.name());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.modify(categoryCommand.name(), categoryCommand.color(), categoryCommand.imgUrl(),
                categoryCommand.description());
    }

    private void checkDuplicatedCategoryName(String name) {
        categoryRepository.findByName(name).ifPresent(category -> {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        });
    }

    public CategoryInfo getCategoryInfo(final Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return CategoryInfo.from(category);
    }

    public CategoryPageInfo getCategoryList(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        return CategoryPageInfo.from(categories);
    }

    public void deleteCategory(final Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        categoryRepository.delete(category);
    }
}
