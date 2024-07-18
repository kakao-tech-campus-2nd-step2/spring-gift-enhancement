package gift.product.application.dto.request;

import gift.product.service.dto.CategoryParam;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull
        String name,
        @NotNull
        String color,
        String imgUrl,
        String description
) {
    public CategoryParam toCategoryParam() {
        return new CategoryParam(name, color, imgUrl, description);
    }
}
