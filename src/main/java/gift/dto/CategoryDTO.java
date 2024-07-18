package gift.dto;


import gift.model.Category;

public class CategoryDTO {
    private int id;
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    public CategoryDTO(int id, String name, String color, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static CategoryDTO convertDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
    }

    public int getId() {
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

    public void setId(int id) {
        this.id = id;
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
}
