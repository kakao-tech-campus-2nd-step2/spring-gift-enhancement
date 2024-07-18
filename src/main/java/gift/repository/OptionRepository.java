package gift.repository;

import gift.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("SELECT o FROM Option o JOIN FETCH o.product p WHERE p.id= :productId")
    List<Option> findAllByProductIdFetchJoin(Long productId);

    @Query("SELECT o FROM Option o JOIN FETCH o.product p WHERE o.id= :id")
    Optional<Option> findByIdFetchJoin(Long id);
}
