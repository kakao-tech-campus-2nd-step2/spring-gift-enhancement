package gift.repository.product;

import gift.model.product.Category;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long aLong);

}
