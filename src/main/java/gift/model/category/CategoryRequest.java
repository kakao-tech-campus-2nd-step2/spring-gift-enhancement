package gift.model.category;

public class CategoryRequest {

    private String name;

    private String color;

    private String imageUrl;

    private String description;

    public CategoryRequest() {
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

    public Category toEntity() {
        return new Category(null, this.name, this.color, this.imageUrl, this.description);
    }
}
