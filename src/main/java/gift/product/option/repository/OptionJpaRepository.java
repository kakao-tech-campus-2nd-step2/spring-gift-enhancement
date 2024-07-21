package gift.product.option.repository;

import gift.product.entity.Product;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionJpaRepository extends JpaRepository<Option, Long>, OptionRepository {

    @Override
    default Options findAllByProduct(Product product) {
        return new Options(this.findByProduct(product));
    }

    List<Option> findByProduct(Product product);
}
