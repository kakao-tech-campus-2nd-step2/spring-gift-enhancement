package gift.product.validation;

import static gift.product.exception.GlobalExceptionHandler.DUPLICATE_OPTION_NAME;
import static gift.product.exception.GlobalExceptionHandler.LAST_OPTION;
import static gift.product.exception.GlobalExceptionHandler.LEAST_QUANTITY;
import static gift.product.exception.GlobalExceptionHandler.NOT_EXIST_ID;
import static gift.product.exception.GlobalExceptionHandler.OVER_100MILLION;

import gift.product.dto.OptionDTO;
import gift.product.exception.DuplicateException;
import gift.product.exception.InvalidIdException;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.OptionRepository;
import java.util.Collection;
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
        List<Option> options = optionRepository.findAllByProduct(product);
        isOver100Million(options.size());
        isDuplicateName(options, optionDTO);
        isNegative(optionDTO.getQuantity());
    }

    public void update(Product product, OptionDTO optionDTO) {
        isExist(optionDTO.getId());
        List<Option> options = optionRepository.findAllByProduct(product);
        isDuplicateName(options, optionDTO);
        isNegative(optionDTO.getQuantity());
    }

    public void delete(Long id) {
        isExist(id);
        isLastOption(optionRepository.countAllByProduct(id));
    }

    public void isExist(Long id) {
        if(optionRepository.existsById(id))
            throw new InvalidIdException(NOT_EXIST_ID);
    }

    public void isOver100Million(int size) {
        if(size >= 99_999_999)
            throw new RuntimeException(OVER_100MILLION);
    }

    public void isDuplicateName(Collection<Option> options, OptionDTO optionDTO) {
        if(options.stream()
            .anyMatch(option -> option.isSameName(optionDTO.getName())
            )
        )
            throw new DuplicateException(DUPLICATE_OPTION_NAME);
    }

    public void isNegative(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException(LEAST_QUANTITY);
    }

    public void isLastOption(int cnt) {
        if(cnt < 2)
            throw new IllegalStateException(LAST_OPTION);
    }

}
