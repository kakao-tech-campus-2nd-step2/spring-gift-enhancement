package gift.option.repository;

import gift.option.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

interface OptionRepository extends JpaRepository<Option, Long> {

}
