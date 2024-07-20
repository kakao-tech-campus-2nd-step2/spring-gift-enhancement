package gift.product.domain;

public class CreateCategoryRequest {

    private String name;
    private String description;
    private String imageUrl;

    public CreateCategoryRequest(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
