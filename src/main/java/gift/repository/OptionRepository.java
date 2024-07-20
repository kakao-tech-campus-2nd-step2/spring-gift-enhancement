package gift.repository;

import gift.model.product.Option;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE Option o SET o.amount = o.amount - :amount WHERE o.id = :id")
    void subtractById(@Param("id") long id, @Param("amount") int amount);

}
