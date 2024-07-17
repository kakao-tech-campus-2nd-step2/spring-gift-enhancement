package gift.dto.requestDTO;

import gift.domain.Category;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
    @NotNull
    String name,
    @NotNull
    String color,
    @NotNull
    String imageUrl,
    String description) {

    public static Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        return new Category(categoryRequestDTO.name(), categoryRequestDTO.color(),
            categoryRequestDTO.imageUrl(), categoryRequestDTO.description());
    }
}
