package gift.domain.dto.request;

import gift.domain.annotation.RestrictedSpecialChars;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.Range;

public record OptionUpdateRequest(
    @NotNull
    @Size(min = 1, max = 50)
    @RestrictedSpecialChars
    String name,
    @NotNull
    String action,
    @NotNull
    @Range(min = 1, max = 100_000_000)
    Integer quantity
) {

}
