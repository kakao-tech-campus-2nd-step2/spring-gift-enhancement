package gift.domain.option;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @EntityGraph(attributePaths = {"product"})
    List<Option> findAllByProductId(Long productId);
}
