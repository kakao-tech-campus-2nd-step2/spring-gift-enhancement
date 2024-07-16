package gift.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryResponse (Long id, String name, String color, String imageUrl, String description){
    public CategoryResponse(Category category) {
        this(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
    }
}
