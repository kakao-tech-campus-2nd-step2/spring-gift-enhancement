package gift.main.repository;

import gift.main.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option,Long> {
    Optional<List<Option>>  findAllByOptionListId(Long id);
}
