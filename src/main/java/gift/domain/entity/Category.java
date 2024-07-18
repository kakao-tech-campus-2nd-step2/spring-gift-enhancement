package gift.domain.entity;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String description;

    protected Category() {
    }

    public Category(String name, String color, String imageUrl, @Nullable String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = Objects.requireNonNullElse(description, "");
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

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", color='" + color + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
