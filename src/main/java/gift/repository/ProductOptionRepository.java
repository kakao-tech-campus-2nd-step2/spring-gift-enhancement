package gift.repository;

import gift.domain.ProductOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProductId(Long productId);

    boolean existsByNameAndProductId(String name, Long productId);

}
