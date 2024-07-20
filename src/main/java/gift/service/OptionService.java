package gift.service;

import gift.entity.Option;
import gift.entity.OptionDTO;
import gift.exception.ResourceNotFoundException;
import gift.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option save(OptionDTO optionDTO) {
        Option option = new Option();
        option.setOptionDTO(optionDTO);
        return optionRepository.save(option);
    }

    public Option findById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + id));
    }

    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    public Option update(Long id, OptionDTO optionDTO) {
        Option option = findById(id);
        option.setOptionDTO(optionDTO);
        return optionRepository.save(option);
    }

    public void delete(Long id) {
        optionRepository.deleteById(id);
    }

    public Option subtract(Long id, int amount) {
        Option option = findById(id);
        if (option.getQuantity() < amount) {
            throw new IllegalArgumentException("Not enough quantity");
        }
        option.subtract(amount);
        return optionRepository.save(option);
    }
}
