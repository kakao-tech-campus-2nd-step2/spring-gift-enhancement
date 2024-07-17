package gift.product.repository;

import gift.product.model.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    void deleteAllByProductId(Long productId);
    @Transactional
    void deleteById(Long id);
    Page<Option> findAllByProductId(Long productId, Pageable pageable);
}
