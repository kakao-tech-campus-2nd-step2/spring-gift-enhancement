package gift.domain.service;

import gift.domain.dto.response.OptionDetailedResponse;
import gift.domain.entity.Option;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.repository.OptionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<OptionDetailedResponse> getAllOptions() {
        return OptionDetailedResponse.of(optionRepository.findAll());
    }

    public Option getOptionById(Long id) {
        return optionRepository.findById(id).orElseThrow(OptionNotFoundException::new);
    }
}
