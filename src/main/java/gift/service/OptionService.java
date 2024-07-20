package gift.service;

import gift.entity.Option;
import gift.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;


    public Optional<Option> getOptionByName(String name) {
        return optionRepository.findByName(name);
    }

    public Set<Option> getAllOptions() {
        return new HashSet<>(optionRepository.findAll());
    }

    public Optional<Option> getOptionById(Long id) {
        return optionRepository.findById(id);
    }

    public Set<Option> getOptionsByProductId(Long productId) {
        return optionRepository.findByProductId(productId);
    }

    public Option saveOption(Option option) {
        return optionRepository.save(option);
    }

    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    public Option updateOption(Long id, Option optionDetails) {
        Optional<Option> optionOptional = optionRepository.findById(id);
        if (optionOptional.isPresent()) {
            Option option = optionOptional.get();
            option.setName(optionDetails.getName());
            option.setQuantity(optionDetails.getQuantity());
            return optionRepository.save(option);
        } else {
            throw new RuntimeException("Option not found with id " + id);
        }
    }
}
