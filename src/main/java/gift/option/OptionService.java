package gift.option;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option addOption(OptionRequest optionRequest){
        isValidRequest(optionRequest);

        return optionRepository.save(optionRequest.toEntity());
    }

    public Option deleteOption(Long id){
        Option option = optionRepository.findById(id).orElseThrow();
        optionRepository.deleteById(option.getId());

        return option;
    }

    public void updateOption(OptionRequest optionRequest){
        isValidRequest(optionRequest);
        Option option = optionRepository.findById(optionRequest.getOptionId()).orElseThrow();
        option.update(optionRequest.getName(), optionRequest.getQuantity());

        optionRepository.save(option);
    }

    public List<Option> findAllByProductId(Long id){
        return optionRepository.findAllByProductId(id);
    }

    private void isValidRequest(OptionRequest optionRequest){
        if(isExistName(optionRequest)){
            throw new IllegalArgumentException(" 동일한 상품 내의 옵션 이름은 중복될 수 없습니다. ");
        }
    }

    private boolean isExistName(OptionRequest optionRequest){
        return findAllByProductId(optionRequest.getProductId())
            .stream()
            .map(Option::getName)
            .anyMatch(optionRequest.getName()::equals);
    }

}
