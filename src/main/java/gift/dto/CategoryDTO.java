package gift.dto;

public class CategoryDTO {
    private String name;
    private String color;
    private String description;
    private String imageUrl;

    public CategoryDTO() {
    }

    public CategoryDTO(String name, String color, String description, String imageUrl) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
