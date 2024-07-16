package gift.dto.wish;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;

public record WishRequest(
    @NotNull(message = REQUIRED_FIELD_MISSING)
    Long memberId,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    Long productId
) {

}
