package gift.dto.member;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;

public record MemberRequest(
    Long id,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String email,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String password
) {

}
