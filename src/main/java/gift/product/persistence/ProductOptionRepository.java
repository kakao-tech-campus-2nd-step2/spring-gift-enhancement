package gift.product.persistence;

import gift.product.domain.ProductOption;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findByProductIdAndId(Long productId, Long id);

    List<ProductOption> findByProductId(Long productId);
}