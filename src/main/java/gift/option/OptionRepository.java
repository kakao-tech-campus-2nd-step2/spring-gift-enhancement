package gift.option;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Long, Option> {

    Option save(Option option);

    Options findAllByProductId(Long productId);

    Optional<Option> findById(Long id);

    void deleteById(Long id);
}
