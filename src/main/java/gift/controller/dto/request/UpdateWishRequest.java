package gift.controller.dto.request;

import jakarta.validation.constraints.Min;

public record UpdateWishRequest(
        @Min(1)
        Long productId,
        @Min(0)
        int productCount
) {
}
