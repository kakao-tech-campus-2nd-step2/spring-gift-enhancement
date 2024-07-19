package gift.repository.option;

import gift.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionSpringDataJpaRepository  extends JpaRepository<Option, Long> {
}
