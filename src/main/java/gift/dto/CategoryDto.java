package gift.dto;

import gift.constants.ErrorMessage;
import gift.entity.Category;
import jakarta.validation.constraints.NotBlank;

public class CategoryDto {

    private Long id;
    @NotBlank(message = ErrorMessage.CATEGORY_NAME_NOT_BLANK_MSG)
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    protected CategoryDto() {
    }

    public CategoryDto(Category category) {
        this(category.getId(), category.getName(), category.getColor(), category.getImageUrl(),
            category.getDescription());
    }

    public CategoryDto(Long id, String name, String color, String imageUrl, String description) {
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
}
