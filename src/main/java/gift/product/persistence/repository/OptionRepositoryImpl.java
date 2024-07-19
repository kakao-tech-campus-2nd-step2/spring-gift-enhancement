package gift.product.persistence.repository;

import gift.product.persistence.entity.Option;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OptionRepositoryImpl implements OptionRepository{

    private final OptionJpaRepository optionJpaRepository;

    public OptionRepositoryImpl(OptionJpaRepository optionJpaRepository) {
        this.optionJpaRepository = optionJpaRepository;
    }

    @Override
    public List<Long> saveAll(List<Option> options) {
        var savedOptions = optionJpaRepository.saveAll(options);
        return savedOptions.stream()
            .map(Option::getId)
            .toList();
    }

    @Override
    public List<Option> getOptionsByProductId(Long productId) {
        return optionJpaRepository.findByProductId(productId);
    }
}
