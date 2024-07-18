package gift.product.infrastructure.persistence.entity;

import gift.core.BaseEntity;
import gift.core.domain.product.ProductCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategoryEntity extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public ProductCategoryEntity() {
    }

    public ProductCategoryEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public ProductCategoryEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ProductCategory toDomain() {
        return new ProductCategory(getId(), name);
    }

    public static ProductCategoryEntity toEntity(ProductCategory productCategory) {
        return new ProductCategoryEntity(productCategory.id(), productCategory.name());
    }
}
