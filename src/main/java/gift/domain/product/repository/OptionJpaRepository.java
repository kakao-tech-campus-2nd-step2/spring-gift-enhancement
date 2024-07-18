package gift.domain.product.repository;

import gift.domain.product.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long> {

}
