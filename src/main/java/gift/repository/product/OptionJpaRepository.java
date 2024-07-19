package gift.repository.product;

import gift.model.product.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long>, OptionRepository {

}
