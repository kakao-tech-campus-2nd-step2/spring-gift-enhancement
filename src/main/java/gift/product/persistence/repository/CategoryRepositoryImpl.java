package gift.product.persistence.repository;

import gift.global.exception.ErrorCode;
import gift.global.exception.NotFoundException;
import gift.product.persistence.entity.Category;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category getReferencedCategory(Long categoryId) {
        return categoryJpaRepository.getReferenceById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public Long saveCategory(Category category) {
        return categoryJpaRepository.save(category).getId();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                    ErrorCode.DB_NOT_FOUND,
                    "Category with id " + id + " not found")
            );
    }
}
