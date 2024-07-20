package gift.dto;

import gift.domain.category.Category;

public class CategoryResponseDto {
    private final Long id;
    private final String name;
    private final String color;
    private final String imageUrl;
    private final String description;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.color = category.getColor();
        this.imageUrl = category.getImageUrl();
        this.description = category.getDescription();
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
}
