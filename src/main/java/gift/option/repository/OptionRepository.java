package gift.option.repository;

import gift.global.MyCrudRepository;
import gift.option.domain.Option;

public interface OptionRepository extends MyCrudRepository<Option, Long> {
    boolean existsById(Long id);

}
