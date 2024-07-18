package gift.main.repository;

import gift.main.entity.OptionList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionListRepository extends JpaRepository<OptionList,Long> {
    Optional<OptionList> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}
