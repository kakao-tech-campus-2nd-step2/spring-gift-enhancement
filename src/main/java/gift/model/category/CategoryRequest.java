package gift.model.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    Long id,
    @NotBlank
    String name,
    String color,
    String imageUrl,
    String description) {
    public Category toEntity(String name, String color, String imageUrl, String description) {
        return new Category(name, color, imageUrl, description);
    }

}
