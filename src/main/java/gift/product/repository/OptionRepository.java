package gift.product.repository;

import gift.product.model.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    void deleteAllByProductId(Long productId);
    Page<Option> findAllByProductId(Long productId, Pageable pageable);
}
