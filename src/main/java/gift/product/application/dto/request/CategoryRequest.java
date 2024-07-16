package gift.product.application.dto.request;

import gift.product.service.dto.CategoryParam;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull
        String name,
        @NotNull
        String color,
        String imageUrl,
        String description
) {
    public CategoryParam toCategoryParam() {
        return new CategoryParam(name, color, imageUrl, description);
    }
}
