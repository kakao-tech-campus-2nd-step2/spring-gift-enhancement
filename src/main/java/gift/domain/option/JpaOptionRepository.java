package gift.domain.option;

import gift.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByProductId(Long productId);

    List<Option> findAllByProduct(Product product);
}
