package gift.repository.option;

import gift.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionSpringDataJpaRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);

    Optional<Option> findOptionByName(String name);

    Long countOptionByProductId(Long ProductId);

    Optional<Option> findByIdAndProductId(Long optionId, Long productId);
}
