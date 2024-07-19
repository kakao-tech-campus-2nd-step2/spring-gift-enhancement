package gift.product.persistence.repository;

import gift.product.persistence.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

}
