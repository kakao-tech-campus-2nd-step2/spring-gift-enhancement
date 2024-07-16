package gift.product.persistence.repository;

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
}
