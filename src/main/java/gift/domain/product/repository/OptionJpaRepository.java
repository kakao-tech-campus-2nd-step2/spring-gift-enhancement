package gift.domain.product.repository;

import gift.domain.product.entity.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o join fetch o.product where o.id = :productId")
    List<Option> findAllByProductId(Long productId);
}
