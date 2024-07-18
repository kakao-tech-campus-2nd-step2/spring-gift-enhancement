package gift.main.service;

import gift.main.dto.OptionRequest;
import gift.main.entity.Option;
import gift.main.entity.OptionList;
import gift.main.repository.OptionListRepository;
import gift.main.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionListRepository optionListRepository;
    private final OptionRepository optionRepository;


    public OptionService(OptionListRepository optionListRepository, OptionRepository optionRepository) {
        this.optionListRepository = optionListRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public void addOption(Long productId, OptionRequest optionRequest) {
        Option option = new Option(optionRequest.name(), optionRequest.num(), optionListRepository.findByProductId(productId).get());
        optionRepository.save(option);
    }
}
