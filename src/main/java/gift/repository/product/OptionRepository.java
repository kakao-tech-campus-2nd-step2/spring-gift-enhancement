package gift.repository.product;

import gift.model.product.Option;
import java.util.List;
import java.util.Optional;

public interface OptionRepository {

    void deleteById(Long id);

    Optional<Option> findById(Long id);

    Option save(Option option);

    List<Option> findByProductId(Long productId);

    boolean existsById(Long optionId);
    
}
