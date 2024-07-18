package gift.domain.option;

import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.global.exception.BusinessException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final JpaProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, JpaProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }
    public List<Option> getOptions() {
        List<Option> options = optionRepository.findAll();
        return options;
    }

    public List<Option> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findAllByProductId(productId);
        return options;
    }

    public void deleteById(Long optionId) {
        Optional<Option> option = optionRepository.findById(optionId);
        if (option.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "삭제할 옵션이 존재하지 않음");
        }
        // 해당 옵션을 참조하는 상품 있는지 확인
        if (option.get().getProduct() == null) {
            optionRepository.deleteById(optionId);
            return;
        }
        throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 옵션을 사용하는 상품 존재, 삭제 불가");
    }
}
