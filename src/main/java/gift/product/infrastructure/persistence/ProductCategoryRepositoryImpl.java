package gift.product.infrastructure.persistence;

import gift.core.domain.product.ProductCategory;
import gift.core.domain.product.ProductCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    private final JpaProductCategoryRepository jpaProductCategoryRepository;

    public ProductCategoryRepositoryImpl(
            JpaProductCategoryRepository jpaProductCategoryRepository
    ) {
        this.jpaProductCategoryRepository = jpaProductCategoryRepository;
    }

    public List<ProductCategory> findAll() {
        return jpaProductCategoryRepository
                .findAll()
                .stream()
                .map(ProductCategoryEntity::toDomain)
                .toList();
    }

    public ProductCategory save(ProductCategory category) {
        return jpaProductCategoryRepository
                .save(ProductCategoryEntity.toEntity(category))
                .toDomain();
    }

    public void remove(Long id) {
        jpaProductCategoryRepository.deleteById(id);
    }

    public Optional<ProductCategory> findById(Long id) {
        return jpaProductCategoryRepository
                .findById(id)
                .map(ProductCategoryEntity::toDomain);
    }

    @Override
    public Optional<ProductCategory> findByName(String name) {
        return jpaProductCategoryRepository
                .findByName(name)
                .map(ProductCategoryEntity::toDomain);
    }
}
