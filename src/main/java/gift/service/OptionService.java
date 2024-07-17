package gift.service;

import gift.controller.dto.response.OptionResponse;
import gift.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<OptionResponse> getAllOptions(Long id) {
        return optionRepository.findAllByProductId(id).stream()
                .map(OptionResponse::from)
                .toList();
    }
}
