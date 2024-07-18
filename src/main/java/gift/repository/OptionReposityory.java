package gift.repository;

import gift.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionReposityory extends JpaRepository<Option, Long> {
}
