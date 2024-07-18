package gift.service;

import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    private boolean isDuplicateName(Option option){
        Optional<Option> options = optionRepository.findByIdAndName(
            option.getProduct().getId(), option.getName()
        );
        return options.isPresent();
    }
    public void addOption(Product product,Option option) {
        if(isDuplicateName(option)){
            throw new IllegalArgumentException();
        }
        optionRepository.save(option);
    }

    public void deleteOption(Option option){
        optionRepository.delete(option);
    }

}
