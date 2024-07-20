package gift.product.business.dto;

import gift.product.persistence.entity.Option;
import java.util.List;

public class OptionIn {

    public record Create(
        String name,
        Integer quantity
    ) {

        public Option toOption() {
            return new Option(name(), quantity());
        }
    }

}
