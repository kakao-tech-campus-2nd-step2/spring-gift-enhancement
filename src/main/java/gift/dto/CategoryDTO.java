package gift.dto;

import gift.domain.Category;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(
    Long id,

    @NotNull
    String name,

    @NotNull
    String color,

    @NotNull
    String imageUrl,

    @NotNull
    String description
) {

    public Category toEntity() {
        return new Category(name, color, imageUrl, description);
    }
}
