package gift.repository;

import gift.model.Option;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByProductId(Long productId, Pageable pageable);

    boolean existsProductOptionByProductIdAndName(Long productId, String name);
}
