package gift.option.repository;

import gift.option.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OptionRepository extends JpaRepository<Option, Long> {

}
