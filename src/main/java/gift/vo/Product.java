package gift.vo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category_id_ref_category_id"))
    Category category;

    @NotNull
    @Size(max = 15)
    private String name;

    private Integer price;

    @NotNull
    private String imageUrl;

    public Product() {}

    public Product(Category category, String name, int price, String imageUrl) {
        this(null, category, name, price, imageUrl);
    }

    public Product(Long id, Category category, String name, int price, String imageUrl) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public java.lang.Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
