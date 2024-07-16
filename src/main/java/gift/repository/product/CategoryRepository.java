package gift.repository.product;

import gift.model.product.Category;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long aLong);

    Category save(Category category);

    void deleteById(Long id);

    List<Category> findAll();

}
