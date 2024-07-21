package gift.product.option.repository;

import gift.product.entity.Product;
import gift.product.option.entity.Options;

public interface OptionRepository {

    Options findAllByProduct(Product product);
}
