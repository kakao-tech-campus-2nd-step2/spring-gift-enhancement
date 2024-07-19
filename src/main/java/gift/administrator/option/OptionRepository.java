package gift.administrator.option;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByProductId(long productId);

    Boolean existsByNameAndProductId(String name, long productId);

    Boolean existsByNameAndProductIdAndIdNot(String name, long productId, long optionId);

    Boolean existsByName(String name);

    @Transactional
    void deleteByProductId(long productId);
}
