package gift.category;

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
