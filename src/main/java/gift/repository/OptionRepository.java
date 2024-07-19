package gift.repository;

import gift.domain.Option;
import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByProduct(Product product);
}
