package gift.repository;

import gift.model.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    @Query("SELECT o FROM Option o WHERE o.product.id = :productId")
    Page<Option> findAllByProductId(@Param("productId") Long productId, Pageable pageable);

    Optional<Option> findByProductId(Long productId);
}
