package gift.service;

import gift.model.product.Option;
import gift.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository){
        this.optionRepository = optionRepository;
    }

    public List<Option> getAllOptionsById(Long productId){
        return optionRepository.findByProductId(productId);
    }
}
