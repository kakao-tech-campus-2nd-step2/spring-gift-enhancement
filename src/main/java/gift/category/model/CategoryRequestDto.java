package gift.category.model;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
    @NotBlank
    String name,
    String color,
    String imageUrl,
    String description
) {

    public Category toEntity() {
        return new Category(
            name,
            color,
            imageUrl,
            description
        );
    }
}
