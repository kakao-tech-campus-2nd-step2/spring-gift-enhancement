package gift.repository;

import gift.entity.OptionName;
import gift.entity.Product;
import gift.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    boolean existsByProductAndOptionName(Product product, OptionName optionName);

}
