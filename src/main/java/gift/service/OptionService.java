package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    public List<Option> getOptions(List<OptionRequest> options) {
        return options.stream()
                .map(optionRequest -> new Option(optionRequest.name(), optionRequest.quantity()))
                .toList();
    }
}
