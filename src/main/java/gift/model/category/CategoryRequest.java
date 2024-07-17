package gift.model.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank
    String name,
    @NotBlank
    String color,
    @NotBlank
    String imageUrl,
    @NotBlank
    String description
) {

    public Category toEntity() {
        return new Category(null, name, color, imageUrl, description);
    }
}
