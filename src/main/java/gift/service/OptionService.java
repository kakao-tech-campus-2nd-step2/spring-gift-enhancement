package gift.service;

import gift.repository.OptionRepository;
import gift.vo.Option;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> getOptionsPerProduct(Long id) {
        return optionRepository.findAllByProductId(id).orElseThrow(
                () -> new IllegalArgumentException("해당 상품의 옵션이 존재하지 않습니다."));
    }
}
