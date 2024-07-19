package gift.service;

import gift.domain.Option;
import gift.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> getProductOptions(Long product_id) {
        return optionRepository.getOptionsByProductId(product_id);
    }
}
