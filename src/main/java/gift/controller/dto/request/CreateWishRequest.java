package gift.controller.dto.request;

import jakarta.validation.constraints.Min;

public record CreateWishRequest(
        @Min(1)
        Long productId
) {
}
