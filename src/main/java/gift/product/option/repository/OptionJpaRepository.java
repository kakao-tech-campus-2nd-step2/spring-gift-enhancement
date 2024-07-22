package gift.product.option.repository;

import gift.product.entity.Product;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import jakarta.persistence.LockModeType;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long>, OptionRepository {

    @Override
    default Options findAllByProduct(Product product) {
        return new Options(new HashSet<>(this.findByProduct(product)));
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Option o WHERE o.id = :id")
    Optional<Option> findByIdWithPessimisticLocking(Long id);

    List<Option> findByProduct(Product product);
}
