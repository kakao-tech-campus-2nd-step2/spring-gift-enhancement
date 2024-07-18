package gift.repository;

import gift.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaDao extends JpaRepository<Option, Long> {

    List<Option> findAllByProduct_id(Long productId);
}
