package gift.product.validation;

import gift.product.dto.ProductDTO;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidIdException;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static gift.product.exception.GlobalExceptionHandler.*;

@Component
public class ProductValidation {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public ProductValidation(
        ProductRepository productRepository,
        OptionRepository optionRepository
    ) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public void registerValidation(ProductDTO productDTO) {
        System.out.println("[ProductValidation] registerValidation()");
        if(productDTO.getName().contains("카카오"))
            throw new InstanceValueException(CONTAINS_PRODUCT_NAME_KAKAO);
        if(productDTO.getPrice() <= 0)
            throw new InstanceValueException(PRODUCT_PRICE_NOT_POSITIVE);
    }

    public void updateValidation(Long id, ProductDTO productDTO) {
        System.out.println("[ProductValidation] updateValidation()");
        validateExistId(id);
        registerValidation(productDTO);
    }

    public void deleteValidation(Long id) {
        System.out.println("[ProductValidation] deleteValidation()");
        validateExistId(id);
        if(isExistOptions(id))
            optionRepository.deleteAllByProductId(id);
    }
    
    private void validateExistId(Long id) {
        System.out.println("[ProductValidation] validateExistId()");
        if(!productRepository.existsById(id))
            throw new InvalidIdException(NOT_EXIST_ID);
    }

    private boolean isExistOptions(Long productId) {
        return optionRepository.existsByProductId(productId);
    }
}
