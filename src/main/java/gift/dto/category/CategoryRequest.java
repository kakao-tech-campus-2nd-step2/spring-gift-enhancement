package gift.dto.category;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    Long id,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String name,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String color,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String imageUrl,

    String description
) {

}
