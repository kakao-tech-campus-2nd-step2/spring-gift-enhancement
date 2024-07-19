package gift.repository;

import gift.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);
//    boolean existsByProductIdAndName(Long productId, String name);
}
