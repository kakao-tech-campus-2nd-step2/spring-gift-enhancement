package gift.repository.option;

import gift.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {

    Optional<Option> findOptionByNameAndProductId(String name, Long productId);

    List<Option> findOptionsByProductId(Long productId);

    Long countOptionByProductId(Long productId);
}
