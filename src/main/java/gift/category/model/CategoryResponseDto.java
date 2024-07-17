package gift.category.model;

public class CategoryResponseDto {

    private Long id;
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    public CategoryResponseDto(Long id, String name, String color, String imageUrl,
        String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Long getId() {
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

    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription()
        );
    }
}
