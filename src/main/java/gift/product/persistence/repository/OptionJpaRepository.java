package gift.product.persistence.repository;

import gift.product.persistence.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    List<Option> findByProductId(Long productId);

    void deleteAllByProductId(Long id);

    void deleteAllByProductIdIn(List<Long> productIds);
}
