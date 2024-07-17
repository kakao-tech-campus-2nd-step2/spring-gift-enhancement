package gift.category.model;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
    @NotBlank
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(String name, String color, String imageUrl, String description) {
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

    public Category toEntity(){
        return new Category(
            name,
            color,
            imageUrl,
            description
        );
    }
}
