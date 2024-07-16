package gift.product.persistence.entity;

import gift.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product",
    indexes = {@Index(name = "idx_modified_date", columnList = "modified_date")}
)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer price;
    @NotNull
    private String url;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, String description, Integer price, String url, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
        this.category = category;
    }

    public Product() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void update(String name, String description, Integer price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
