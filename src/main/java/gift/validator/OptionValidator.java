package gift.validator;

import gift.constants.Messages;
import gift.domain.Option;
import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OptionValidator {
    private final ProductRepository productRepository;

    public OptionValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateOptionName(String optionName){
        if (optionName == null || optionName.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름을 비우거나 공백으로 설정할 수 없습니다");
        }
        if (optionName.length() > 50) {
            throw new IllegalArgumentException("옵션명은 공백 포함하여 최대 50자까지 입력할 수 있습니다");
        }
        if (!optionName.matches("^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_가-힣]*$")) {
            throw new IllegalArgumentException("특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.");
        }
    }

    public void validateDuplicateOptionName(List<Option> options,String name){
        for(Option option:options){
            if(option.getName().equals(name)){
                throw new IllegalArgumentException(Messages.DUPLICATE_OPTION_NAME_MESSAGE);
            }
        }
    }

    public void validateOptionQuantity(int quantity){
        if(quantity < 0 || quantity > 100000000){
            throw new IllegalArgumentException(Messages.QUANTITY_OUT_OF_RANGE_MESSAGE);
        }
    }

    public void validateUpdateQuantity(int newQuantity) throws IllegalArgumentException {
        if(newQuantity < 0){
            throw new IllegalArgumentException(Messages.QUANTITY_OUT_OF_RANGE_MESSAGE);
        }
    }

    //  상품 당 옵션 개수 확인
    public void validateNumberOfOptions(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_MESSAGE));
        if(product.getOptions().size() < 2){
            throw new IllegalArgumentException(Messages.OPTION_BELOW_MINIMUM_MESSAGE);
        }
    }
}
