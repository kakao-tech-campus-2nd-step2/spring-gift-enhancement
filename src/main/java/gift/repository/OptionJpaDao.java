package gift.repository;

import gift.entity.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaDao extends JpaRepository<Option, Long> {

    List<Option> findAllByProduct_Id(Long productId);

    Optional<Option> findByNameAndProduct_Id(String optionName, Long productId);
}
