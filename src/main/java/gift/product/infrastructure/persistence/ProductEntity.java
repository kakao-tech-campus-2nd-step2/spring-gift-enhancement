package gift.product.infrastructure.persistence;

import gift.core.domain.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategoryEntity category;

    public ProductEntity() {
    }

    public ProductEntity(
            Long id,
            String name,
            Integer price,
            String imageUrl,
            ProductCategoryEntity category
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public ProductEntity(
            String name,
            Integer price,
            String imageUrl,
            ProductCategoryEntity category
    ) {
        this.id = 0L;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toDomain() {
        return new Product(id, name, price, imageUrl, category.toDomain());
    }
}
