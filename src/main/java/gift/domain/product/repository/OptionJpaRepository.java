package gift.domain.product.repository;

import gift.domain.product.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    @Query("delete from Option o where o.product.id = :productId")
    @Modifying
    void deleteAllByProductId(@Param("productId") Long productId);
}
