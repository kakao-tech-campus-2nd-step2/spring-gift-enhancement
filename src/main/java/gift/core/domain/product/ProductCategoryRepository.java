package gift.core.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {

    Optional<ProductCategory> findById(Long id);

    Optional<ProductCategory> findByName(String name);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory category);

    void remove(Long id);

}
