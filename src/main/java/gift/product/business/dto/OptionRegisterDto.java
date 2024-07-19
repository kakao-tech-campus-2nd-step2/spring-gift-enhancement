package gift.product.business.dto;

import gift.product.persistence.entity.Option;
import gift.product.persistence.entity.Product;

public record OptionRegisterDto(
    String name,
    Integer quantity
) {

    public Option toOption(Product product) {
        return new Option(name(), quantity(), product);
    }
}
