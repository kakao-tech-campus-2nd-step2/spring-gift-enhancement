package gift.service.product.dto;

import gift.controller.product.dto.OptionRequest;
import gift.model.product.Option;
import gift.model.product.Product;
import java.util.List;

public class OptionCommand {

    public record Info(
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

    public record Register(
        List<Info> request
    ) {

        public List<Option> toEntities(Product product) {
            return request.stream()
                .map(info -> info.toEntity(product))
                .toList();
        }
    }
}
