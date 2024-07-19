package gift.option.repository;

import gift.global.MyCrudRepository;
import gift.option.domain.Option;

import java.util.List;

public interface OptionRepository extends MyCrudRepository<Option, Long> {
    boolean existsById(Long id);

    List<Option> findByProductId(Long productId);
}
