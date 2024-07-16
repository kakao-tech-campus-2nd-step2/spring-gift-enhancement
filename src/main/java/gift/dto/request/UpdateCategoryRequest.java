package gift.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
        @NotBlank
        Long id,
        @NotBlank
        String name,
        String color,
        String imageUrl,
        String description
) {
}
