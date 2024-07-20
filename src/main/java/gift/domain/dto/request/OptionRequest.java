package gift.domain.dto.request;

import gift.domain.annotation.RestrictedSpecialChars;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.Range;

public record OptionRequest(
    @NotNull
    @Size(min = 1, max = 50)
    @RestrictedSpecialChars
    String name,
    @NotNull
    @Range(min = 1, max = 100_000_000)
    Integer quantity
) {

    public static OptionRequest of(Option option) {
        return new OptionRequest(option.getName(), option.getQuantity());
    }

    public static List<OptionRequest> of(List<Option> options) {
        return options.stream()
            .map(OptionRequest::of)
            .toList();
    }

    public Option toEntity(Product product) {
        return new Option(product, name, quantity);
    }
}
