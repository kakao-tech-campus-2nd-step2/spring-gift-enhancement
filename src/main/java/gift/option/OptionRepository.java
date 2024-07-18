package gift.option;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Long, Option> {

    Page<Option> findAllByProductId(Long productId);
}
