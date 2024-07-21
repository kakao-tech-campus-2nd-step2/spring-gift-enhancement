package gift.repository;

import gift.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaDao extends JpaRepository<Option, Long> {

}
