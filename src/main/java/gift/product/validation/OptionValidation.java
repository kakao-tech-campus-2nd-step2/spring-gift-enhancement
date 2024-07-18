package gift.product.validation;

import static gift.product.exception.GlobalExceptionHandler.DUPLICATE_OPTION_NAME;
import static gift.product.exception.GlobalExceptionHandler.LAST_OPTION;
import static gift.product.exception.GlobalExceptionHandler.LEAST_QUANTITY;
import static gift.product.exception.GlobalExceptionHandler.OVER_100MILLION;

import gift.product.dto.OptionDTO;
import gift.product.exception.DuplicateException;
import gift.product.model.Product;
import gift.product.repository.OptionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionValidation {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionValidation(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public void register(Product product, OptionDTO optionDTO) {
        List<String> options = optionRepository.findAllByProduct(product);
        if(options.size() >= 99_999_999)
            throw new RuntimeException(OVER_100MILLION);
        if(options.contains(optionDTO.getName()))
            throw new DuplicateException(DUPLICATE_OPTION_NAME);
        isNegative(optionDTO.getQuantity());
    }

    public void update(Product product, OptionDTO optionDTO) {
        List<String> options = optionRepository.findAllByProduct(product);
        if(options.contains(optionDTO.getName()))
            throw new DuplicateException(DUPLICATE_OPTION_NAME);
        isNegative(optionDTO.getQuantity());
    }

    public void delete(Long optionId) {
        Long optionCnt = optionRepository.countAllByProduct(optionId);
        if(optionCnt < 2)
            throw new IllegalStateException(LAST_OPTION);
    }

    public void isNegative(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException(LEAST_QUANTITY);
    }

}
