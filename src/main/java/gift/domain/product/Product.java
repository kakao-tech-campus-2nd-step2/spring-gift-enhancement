package gift.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gift.domain.category.Category;
import gift.domain.product.option.ProductOption;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;


    protected Product() {}

    public Product(String name, Long price, String description, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public void update(String name, Long price, String description, String imageUrl, Category category) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (price != null && price > 0) {
            this.price = price;
        }
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
        if (imageUrl != null && !imageUrl.isEmpty()) {
            this.imageUrl = imageUrl;
        }
        if (category != null) {
            this.category = category;
        }
    }

    public void updateImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            this.imageUrl = imageUrl;
        }
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options;
    }
}
