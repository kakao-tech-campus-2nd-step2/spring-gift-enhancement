package gift.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WishRequest(
        @NotBlank
        Long productId,
        @NotNull
        int quantity
) {
}
