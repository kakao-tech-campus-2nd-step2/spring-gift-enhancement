package gift.Repository;

import gift.Model.Option;
import gift.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProduct(Product product);

    void deleteByProduct(Product product);
}
