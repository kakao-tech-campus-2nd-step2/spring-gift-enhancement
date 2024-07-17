package gift.api.wishlist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WishRequest(
    @NotNull(message = "Product id is mandatory")
    @Positive(message = "Product id must be greater than zero")
    Long productId,
    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be greater than zero")
    Integer quantity
) {}
