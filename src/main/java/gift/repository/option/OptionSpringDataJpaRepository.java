package gift.repository.option;

import gift.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionSpringDataJpaRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);
}
