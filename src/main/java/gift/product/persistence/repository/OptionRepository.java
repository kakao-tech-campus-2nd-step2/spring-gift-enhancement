package gift.product.persistence.repository;

import gift.product.persistence.entity.Option;
import java.util.List;

public interface OptionRepository {

    List<Long> saveAll(List<Option> options);
}
