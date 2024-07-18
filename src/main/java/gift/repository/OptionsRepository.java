package gift.repository;

import gift.model.Options;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {

    List<Options> findAllByProductId(Long productId);

    Optional<Options> findByNameAndProductId(String name, Long productId);

    Long optionsCountByProductId(Long productId);
}
