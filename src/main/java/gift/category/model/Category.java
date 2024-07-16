package gift.category.model;

import gift.common.model.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Category extends BaseEntity {

    private String name;
    private String color;
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

    public void updateInfo(Category category) {
        this.name = category.getName();
        this.color = category.getColor();
        this.imageUrl = category.getImageUrl();
        this.description = category.getDescription();
    }
}
