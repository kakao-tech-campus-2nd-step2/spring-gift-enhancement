package gift.service;

import static gift.util.constants.OptionConstants.OPTION_NAME_DUPLICATE;
import static gift.util.constants.OptionConstants.OPTION_NOT_FOUND;
import static gift.util.constants.OptionConstants.OPTION_REQUIRED;
import static gift.util.constants.ProductConstants.PRODUCT_NOT_FOUND;

import gift.dto.option.OptionCreateRequest;
import gift.dto.option.OptionResponse;
import gift.dto.option.OptionUpdateRequest;
import gift.exception.option.OptionNotFoundException;
import gift.exception.product.ProductNotFoundException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream()
            .map(this::convertToDTO)
            .toList();
    }

    public OptionResponse getOptionById(Long productId, Long optionId) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND + optionId));

        if (!option.isProductIdMatching(productId)) {
            throw new OptionNotFoundException(OPTION_NOT_FOUND + optionId);
        }

        return convertToDTO(option);
    }

    public OptionResponse addOptionToProduct(Long productId,
        OptionCreateRequest optionCreateRequest) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));

        validateDuplicateOptionName(productId, optionCreateRequest.name());

        Option option = new Option(
            optionCreateRequest.name(),
            optionCreateRequest.quantity(),
            product
        );
        Option savedOption = optionRepository.save(option);
        return convertToDTO(savedOption);
    }

    public OptionResponse updateOption(Long productId, Long optionId,
        OptionUpdateRequest optionUpdateRequest) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));

        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND + optionId));

        if (!option.isProductIdMatching(productId)) {
            throw new OptionNotFoundException(OPTION_NOT_FOUND + optionId);
        }

        validateDuplicateOptionName(productId, optionUpdateRequest.name());

        option.update(optionUpdateRequest.name(), optionUpdateRequest.quantity(), product);
        Option updatedOption = optionRepository.save(option);
        return convertToDTO(updatedOption);
    }

    public void deleteOption(Long productId, Long optionId) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND + optionId));

        if (!option.isProductIdMatching(productId)) {
            throw new OptionNotFoundException(OPTION_NOT_FOUND + optionId);
        }

        if (optionRepository.findByProductId(productId).size() == 1) {
            throw new IllegalArgumentException(OPTION_REQUIRED);
        }

        optionRepository.delete(option);
    }

    private void validateDuplicateOptionName(Long productId, String optionName) {
        List<Option> options = optionRepository.findByProductId(productId);
        for (Option option : options) {
            if (option.isNameMatching(optionName)) {
                throw new IllegalArgumentException(OPTION_NAME_DUPLICATE);
            }
        }
    }

    // Mapper methods
    private OptionResponse convertToDTO(Option option) {
        return new OptionResponse(
            option.getId(),
            option.getName(),
            option.getQuantity(),
            option.getProduct().getId()
        );
    }
}
