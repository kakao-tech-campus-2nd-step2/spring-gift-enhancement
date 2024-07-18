package gift.option;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Long, Option> {

    Option save(Option option);

    Page<Option> findAllByProductId(Long productId, Pageable pageable);

    Options findAllByProductId(Long productId);

    Optional<Option> findById(Long id);

    void deleteById(Long id);
}
