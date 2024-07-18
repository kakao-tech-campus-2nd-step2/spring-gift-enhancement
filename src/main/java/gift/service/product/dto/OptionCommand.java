package gift.service.product.dto;

import gift.model.product.Option;
import gift.model.product.Product;

public class OptionCommand {

    public record Register(
        String name,
        Integer quantity
    ) {

        public Option toEntity(Product product) {
            return new Option(name, quantity, product);
        }
    }

    public record Update(
        String name,
        Integer quantity
    ) {

    }
}
