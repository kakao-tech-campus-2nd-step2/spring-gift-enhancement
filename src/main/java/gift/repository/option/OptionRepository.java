package gift.repository.option;

import gift.model.option.Option;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    //    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Option> findById(Long id);
}
