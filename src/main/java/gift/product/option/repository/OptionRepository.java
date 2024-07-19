package gift.product.option.repository;

import gift.product.entity.Product;
import gift.product.option.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByProduct(Product product);
}
