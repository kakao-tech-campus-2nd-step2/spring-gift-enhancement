package gift.util;

import gift.domain.Option;
import gift.domain.Product;

public class OptionFixture {

    public static Option createOption(Product product) {
        return createOption(null, "test", product);
    }

    public static Option createOption(Long id, String name, Product product) {
        return new Option(id, name, 1, product);
    }
}
