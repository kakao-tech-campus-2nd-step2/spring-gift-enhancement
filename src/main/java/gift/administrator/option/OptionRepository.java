package gift.administrator.option;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByProductId(long productId);

    boolean existsByIdAndProductId(long optionId, long productId);

    @Transactional
    void deleteByProductId(long productId);
}
