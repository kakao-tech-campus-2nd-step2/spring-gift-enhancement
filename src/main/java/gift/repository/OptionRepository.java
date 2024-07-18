package gift.repository;

import gift.domain.Option;
import gift.entity.OptionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    OptionEntity save(Option option);

    List<OptionEntity> findAllByProductId(Long ProductId);

    Optional<OptionEntity> findByProductIdAndId(Long ProductId, Long id);

    Optional<OptionEntity> findByProductIdAndName(Long ProductId, String name);

}
