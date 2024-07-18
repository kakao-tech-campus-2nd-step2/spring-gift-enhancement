package gift.entity;

import gift.dto.request.UpdateCategoryRequest;
import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    private String description;

    protected Category() {
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public void update(UpdateCategoryRequest request) {
        this.name = request.name();
        this.color = request.color();
        this.imageUrl = request.imageUrl();
        this.description = request.description();
    }

    public Long getId() {
        return id;
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

    public String getName() {
        return name;
    }
}
