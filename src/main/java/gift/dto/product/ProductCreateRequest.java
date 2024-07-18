package gift.dto.product;

import static gift.util.constants.GeneralConstants.REQUIRED_FIELD_MISSING;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductCreateRequest(
    @NotNull(message = REQUIRED_FIELD_MISSING)
    String name,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    Integer price,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    String imageUrl,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    Long categoryId,

    @NotNull(message = REQUIRED_FIELD_MISSING)
    List<Long> options
) {

}
