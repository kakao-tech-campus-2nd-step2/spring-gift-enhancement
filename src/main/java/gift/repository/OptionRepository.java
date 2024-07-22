package gift.repository;

import gift.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findById(int id);
    int searchQuantityById(int id);
    int updateQuantityWithOptimisticLock(@Param("id") int id, @Param("newQuantity") int newQuantity, @Param("expectedQuantity") int expectedQuantity);
}