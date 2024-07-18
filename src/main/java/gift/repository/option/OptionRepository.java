package gift.repository.option;

import gift.model.option.Option;
import gift.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    boolean existsByProductAndName(Product product, String name);

}
