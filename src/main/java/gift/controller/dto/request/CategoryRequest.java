package gift.controller.dto.request;

import gift.model.Category;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull
        String name,
        @NotNull
        String color,
        @NotNull
        String imageUrl,
        String description
) {
}
