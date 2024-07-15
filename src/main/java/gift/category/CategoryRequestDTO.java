package gift.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryRequestDTO {

    @NotBlank
    String name;
    @NotNull
    String color;
    @NotBlank
    String imageUrl;
    @NotNull
    String description;

    public CategoryRequestDTO(Category category) {
        this.name = category.getName();
        this.color = category.getColor();
        this.imageUrl = category.getImageUrl();
        this.description = category.getDescription();
    }

    public CategoryRequestDTO(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public CategoryRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
