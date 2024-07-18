package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import gift.exception.OptionDuplicateException;
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
}
