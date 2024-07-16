package gift.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddCategoryRequest(
        @NotBlank
        String name,
        String color,
        String imageUrl,
        String description
) {
}
