package gift.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WishRequest(
        @NotBlank
        Long productId,
        @NotBlank
        int quantity
) {
}
