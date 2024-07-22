package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.InsufficientOptionQuantityException;
import gift.exception.OptionDuplicateException;
import gift.exception.OptionNotFoundException;
import gift.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> convertToOptions(List<OptionRequest> options) {
        return options.stream()
                .map(optionRequest -> new Option(optionRequest.name(), optionRequest.quantity()))
                .toList();
    }

    public Option convertToOption(OptionRequest request) {
        return new Option(request.name(), request.quantity());
    }

    public void checkDuplicateOptionName(List<Option> existingOptions, String newOptionName) {
        boolean isDuplicate = existingOptions.stream()
                .anyMatch(option -> option.getName().equals(newOptionName));

        if (isDuplicate) {
            throw new OptionDuplicateException(newOptionName);
        }
    }

    public Option checkOptionIdExist(Long targetOptionId, List<Option> options) {
        for (Option option : options) {
            if (option.getId().equals(targetOptionId)) {
                return option;
            }
        }
        throw new OptionNotFoundException(targetOptionId);
    }

    @Transactional
    public void subtractOptionQuantity(Long targetOptionId, int subtractQuantity) {
        Option targetOption = optionRepository.findByIdWithPessimisticWriteLock(targetOptionId)
                .orElseThrow(() -> new OptionNotFoundException(targetOptionId));

        if (targetOption.getQuantity() < subtractQuantity) {
            throw new InsufficientOptionQuantityException(subtractQuantity);
        }
        targetOption.subtract(subtractQuantity);
    }

    public Option saveOption(Product product, OptionRequest optionRequest) {
        Option option = new Option(optionRequest.name(), optionRequest.quantity());
        option.associateWithProduct(product);
        return optionRepository.save(option);
    }
}
