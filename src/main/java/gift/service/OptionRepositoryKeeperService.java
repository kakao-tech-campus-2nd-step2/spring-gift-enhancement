package gift.service;

import gift.entity.Product;
import gift.repository.OptionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class OptionRepositoryKeeperService {

    private final OptionRepository optionRepository;

    public OptionRepositoryKeeperService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
    public void checkUniqueOptionName(Product product, String optionName) throws DataIntegrityViolationException {
        if(optionRepository.existsByProductAndName(product, optionName))
            throw new DataIntegrityViolationException("이미 있는 옵션 이름입니다.");
    }
}
