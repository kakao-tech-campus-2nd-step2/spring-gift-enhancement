package gift.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    public List<Option> findAllByProductId(Long id);
    public Optional<Option> findByNameAndProductId(String name, Long id);
}
