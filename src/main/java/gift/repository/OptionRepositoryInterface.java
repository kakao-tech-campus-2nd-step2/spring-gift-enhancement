package gift.repository;

import gift.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepositoryInterface extends JpaRepository<Option, Long> {
    Option findOptionByName(String optionName);
}
