package gift.service;

import gift.exception.ProductNotFoundException;
import gift.model.Option;
import gift.repository.OptionRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void updateOption(Long id, Option option) {
        if (!optionRepository.existsById(id)) {
            throw new ProductNotFoundException("Option not found");
        }

        if (optionRepository.existsById(id)) {
            Option option1 = optionRepository.findById(id).get();
            option1.setOptionName(option.getOptionName());
            option1.setQuantity(option.getQuantity());
            optionRepository.save(option1);
        }

    }

    public void deleteOption(Long productId) {
        if (!optionRepository.existsById(productId)) {
            throw new ProductNotFoundException("Option not found");
        }
        optionRepository.deleteById(productId);
    }


    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    public boolean existsOptionById(Long id) {
        return optionRepository.existsById(id);
    }

}
