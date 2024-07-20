package gift.option.service;

import gift.option.domain.Option;
import gift.option.domain.OptionRequest;
import gift.option.repository.OptionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {
    private final OptionJpaRepository optionJpaRepository;
    public OptionService(OptionJpaRepository optionJpaRepository) {
        this.optionJpaRepository = optionJpaRepository;
    }

    // 1. option create
    @Transactional
    public Option createOption(OptionRequest optionRequest) {
        Option option = new Option(optionRequest.getName(), optionRequest.getQuantity());

        // validate
        checkForDuplicateOption(option);

        return optionJpaRepository.save(option);
    }

    // 2. option read
    @Transactional(readOnly = true)
    public Option getOption(Long optionId) {
        return optionJpaRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 다음 id의 옵션은 존재하지 않음 : " + optionId));
    }
    @Transactional(readOnly = true)
    public List<Option> getAllOptions() {
        return optionJpaRepository.findAll();
    }

    // 3. option update
    @Transactional
    public Option updateOption(Long optionId, OptionRequest optionRequest) {
        Option option = optionJpaRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 다음 id의 옵션은 존재하지 않음 : " + optionId));

        // validate
        checkForDuplicateOption(option);

        // update
        option.update(optionRequest.getName(), optionRequest.getQuantity());
        optionJpaRepository.save(option);

        return option;
    }

    // 4. option delete
    @Transactional
    public void deleteOption(Long optionId) {
        Option existingOption = optionJpaRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 다음 id의 옵션은 존재하지 않음 : " + optionId));

        optionJpaRepository.delete(existingOption);
    }

    // 중복성 검사
    public void checkForDuplicateOption(Option option) {
        List<Option> options = optionJpaRepository.findAll();
        for (Option o : options) {
            if (o.equals(option)) {
                throw new IllegalArgumentException("[ERROR] 중복된 옵션 존재 : " + option.getName());
            }
        }
    }

}
