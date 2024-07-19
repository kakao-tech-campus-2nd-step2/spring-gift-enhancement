package gift.product.application.dto.request;

import gift.common.validation.NamePattern;
import gift.product.service.command.ProductOptionCommand;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record ProductOptionRequest(
        @NamePattern
        @NotBlank
        String name,

        @Range(min = 1, max = 99_999_999)
        Integer quantity
) {
    public ProductOptionCommand toProductOptionCommand() {
        return new ProductOptionCommand(name(), quantity());
    }
}
