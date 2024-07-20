package gift.service;

import gift.exception.ProductNotFoundException;
import gift.model.Option;
import gift.repository.OptionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    @Autowired
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public void addOption(List<Option> option) {
        optionRepository.saveAll(option);
    }

    public void updateOption(Long id, Option option) {
        if (!optionRepository.existsById(id)) {
            throw new ProductNotFoundException("Option not found");
        }
        option.setId(id);
        optionRepository.save(option);
    }

    public void deleteOption(Long productId) {
        if (!optionRepository.existsById(productId)) {
            throw new ProductNotFoundException("Option not found");
        }
        optionRepository.deleteById(productId);
    }

    public List<Option> getOptionByProductId(Long productId) {
        if (!optionRepository.existsById(productId)) {
            throw new ProductNotFoundException("Option not found");
        }
        return optionRepository.findByProductId(productId);
    }

    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    public boolean existsOptionById(Long id) {
        return optionRepository.existsById(id);
    }

}
