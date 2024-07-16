package gift.entity;

import gift.dto.CategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    protected Category() {
    }

    public Category(CategoryDto categoryDto) {
        this(categoryDto.getName(), categoryDto.getColor(), categoryDto.getImageUrl(),
            categoryDto.getDescription());
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public void updateCategory(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
        this.color = categoryDto.getColor();
        this.imageUrl = categoryDto.getImageUrl();
        this.description = categoryDto.getDescription();
    }
}
