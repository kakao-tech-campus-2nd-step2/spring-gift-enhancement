package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import java.util.Objects;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT COMMENT '상품 ID'")
    private Long id;

    @Embedded
    @Valid
    private Name name;

    @Column(nullable = false, columnDefinition = "INTEGER COMMENT '상품 가격'")
    private int price;

    @Column(name = "image_url", nullable = false, columnDefinition = "VARCHAR(255) COMMENT '상품 이미지 URL'")
    private String imageUrl;

    @Column(name = "category_id", nullable = false, columnDefinition = "BIGINT COMMENT '카테고리 ID'")
    private Long categoryId;

    protected Product() {}

    public Product(Long id, Name name, int price, String imageUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void update(Name name, int price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}