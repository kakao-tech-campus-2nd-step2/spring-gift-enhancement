package gift.repository;

import gift.model.option.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {
    boolean existsByName(String name);
    List<Option> findAllByItemId(Long id);
    Optional<Option> findByName(String name);
}