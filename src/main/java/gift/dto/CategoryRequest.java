package gift.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String color,
        @NotBlank
        String imageUrl,
        @NotBlank
        String description
) {
}
