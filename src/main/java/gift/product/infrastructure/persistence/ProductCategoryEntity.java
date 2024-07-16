package gift.product.infrastructure.persistence;

import gift.core.domain.product.ProductCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public ProductCategoryEntity() {
    }

    public ProductCategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategoryEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductCategory toDomain() {
        return new ProductCategory(id, name);
    }

    public static ProductCategoryEntity toEntity(ProductCategory productCategory) {
        return new ProductCategoryEntity(productCategory.id(), productCategory.name());
    }


}
