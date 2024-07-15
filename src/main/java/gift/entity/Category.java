package gift.entity;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    private String imageUrl;

    private String description;

    public Category() {
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
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
