package gift.option;

import static gift.exception.ErrorMessage.OPTION_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.OPTION_NOT_FOUND;
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
        if (!productRepository.existsById(productId)) {
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
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));

        if (optionRepository.existsByNameAndProductId(optionDTO.getName(), productId)) {
            throw new IllegalArgumentException(OPTION_ALREADY_EXISTS);
        }

        optionRepository.save(new Option(
            optionDTO.getId(),
            optionDTO.getName(),
            optionDTO.getQuantity(),
            product
        ));
    }

    public void updateOption(long productId, OptionDTO optionDTO) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        Option option = optionRepository.findById(optionDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException(OPTION_NOT_FOUND));

        if (optionRepository.existsByNameAndProductId(optionDTO.getName(), productId)) {
            throw new IllegalArgumentException(OPTION_ALREADY_EXISTS);
        }

        option.updateOption(optionDTO);
        optionRepository.save(option);
    }

    public void deleteOption(long productId, long optionId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new IllegalArgumentException(OPTION_NOT_FOUND));

        optionRepository.delete(option);
    }
}
