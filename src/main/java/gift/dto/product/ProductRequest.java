package gift.dto.product;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    Long id,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String name,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    Integer price,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String imageUrl
) {

}
