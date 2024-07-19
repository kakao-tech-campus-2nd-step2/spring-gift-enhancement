package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import gift.exception.InsufficientOptionQuantityException;
import gift.exception.OptionDuplicateException;
import gift.exception.OptionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

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

    public void subtractOptionQuantity(Option targetOption, int subtractQuantity) {
        if (targetOption.getQuantity() < subtractQuantity) {
            throw new InsufficientOptionQuantityException(subtractQuantity);
        }
        targetOption.subtract(subtractQuantity);
    }
}
