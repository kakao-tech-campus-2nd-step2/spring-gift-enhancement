package gift.DTO.category;

import gift.domain.Category;

public class CategoryResponse {

    private Long id;

    private String name;

    private String color;

    private String description;

    private String imageUrl;

    protected CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, String color,
                            String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static CategoryResponse fromEntity(Category categoryEntity) {
        return new CategoryResponse(
            categoryEntity.getId(),
            categoryEntity.getName(),
            categoryEntity.getColor(),
            categoryEntity.getDescription(),
            categoryEntity.getImageUrl()
        );
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

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
