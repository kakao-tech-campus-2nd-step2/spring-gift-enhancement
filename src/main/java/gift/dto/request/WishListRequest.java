package gift.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WishListRequest(
        @NotBlank
        Long productId,
        @NotBlank
        int amount
) {
}
