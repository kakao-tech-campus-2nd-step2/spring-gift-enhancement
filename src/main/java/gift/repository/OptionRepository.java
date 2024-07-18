package gift.repository;

import gift.entity.Option;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
    public Optional<Option> findByIdAndName(Long id, String name);
}
