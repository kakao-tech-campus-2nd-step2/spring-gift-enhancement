package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name", nullable = false)
    String name;
    @Column(name="color", nullable = false)
    String color;
    @Column(name="imageUrl", nullable = false)
    String imageUrl;
    @Column(name="description", nullable = false)
    String description;

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
}
