package gift.option.repository;

import gift.option.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long> {
}
