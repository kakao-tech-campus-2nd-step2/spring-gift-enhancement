package gift.Service;

import gift.Exception.OptionValidationException;
import gift.Model.Option;
import gift.Model.Product;
import gift.Repository.OptionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionKeeperService {
    private final OptionRepository optionRepository;

    public OptionKeeperService(OptionRepository optionRepository){
        this.optionRepository = optionRepository;
    }

    public void checkUniqueOptionName(Product product, String name){
        optionRepository.findByProduct(product)
                .stream()
                .filter(it->it.getName().equals(name))
                .findFirst()
                .ifPresent(it -> { // 옵션이 존재할 경우
                    throw new OptionValidationException("이미 해당 상품내에 동일한 이름의 옵션이 존재합니다");
                });
    }

    public void checkHasAtLeastOneOption(Product product) {
        List<Option> optionList = optionRepository.findByProduct(product);
        if (optionList.size() == 1 )
            throw new OptionValidationException("상품 내의 옵션은 적어도 1개 이상이여야 합니다.");
    }

    public void checkOptionQuantity(int OptionQuantity, int subtractQuantity) {
        if (OptionQuantity - subtractQuantity < 1)
            throw new IllegalArgumentException("옵션 수량은 최소 1개이상이여야 합니다. 현재 빼려고 하는 수량이 많습니다.");
    }
}
