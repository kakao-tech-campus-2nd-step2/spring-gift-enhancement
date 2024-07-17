package gift.dto.category.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
    @NotNull
    @NotEmpty
    String name
) {

}
