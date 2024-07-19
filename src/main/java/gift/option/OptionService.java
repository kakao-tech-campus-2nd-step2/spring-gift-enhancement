package gift.option;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;

import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(
        OptionRepository optionRepository,
        ProductRepository productRepository
    ) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDTO> getOptions(long productId) {
        if(!productRepository.existsById(productId)){
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        return optionRepository.findAllByProductId(productId)
            .stream()
            .map(option -> new OptionDTO(
                option.getId(),
                option.getName(),
                option.getQuantity()
            ))
            .toList();
    }

    public void addOption(long productId, OptionDTO optionDTO) {

    }

    public void updateOption(long productId, OptionDTO optionDTO) {

    }

    public void deleteOption(long productId, long optionId) {

    }
}
