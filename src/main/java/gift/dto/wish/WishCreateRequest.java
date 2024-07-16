package gift.dto.wish;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;

public record WishCreateRequest(
    @NotNull(message = REQUIRED_FIELD_MISSING)
    Long productId
) {

}
