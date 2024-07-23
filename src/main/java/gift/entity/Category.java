package gift.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @OneToMany(mappedBy = "category")
    private final List<Product> products = new ArrayList<>();
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String color;
    @NotNull
    private String imageUrl;
    @NotNull
    private String description;

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    protected Category() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean emptyCategoryCheck() {
        return products.isEmpty();
    }

    public void updateCategory(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
