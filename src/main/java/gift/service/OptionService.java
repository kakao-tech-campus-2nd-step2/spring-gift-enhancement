package gift.service;

import static gift.util.constants.OptionConstants.INSUFFICIENT_QUANTITY;
import static gift.util.constants.OptionConstants.OPTION_NAME_DUPLICATE;
import static gift.util.constants.OptionConstants.OPTION_NOT_FOUND;
import static gift.util.constants.OptionConstants.OPTION_REQUIRED;

import gift.dto.option.OptionCreateRequest;
import gift.dto.option.OptionResponse;
import gift.dto.option.OptionUpdateRequest;
import gift.dto.product.ProductResponse;
import gift.exception.option.OptionNotFoundException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
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

    public OptionResponse addOptionToProduct(
        Long productId,
        OptionCreateRequest optionCreateRequest
    ) {
        ProductResponse productResponse = productService.getProductById(productId);
        Product product = productService.convertToEntity(productResponse);

        validateDuplicateOptionName(productId, optionCreateRequest.name());

        Option option = new Option(
            optionCreateRequest.name(),
            optionCreateRequest.quantity(),
            product
        );
        Option savedOption = optionRepository.save(option);
        return convertToDTO(savedOption);
    }

    public OptionResponse updateOption(
        Long productId,
        Long optionId,
        OptionUpdateRequest optionUpdateRequest
    ) {
        ProductResponse productResponse = productService.getProductById(productId);
        Product product = productService.convertToEntity(productResponse);

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

    public void subtractOptionQuantity(Long productId, Long optionId, int quantity) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND + optionId));

        if (!option.isProductIdMatching(productId)) {
            throw new OptionNotFoundException(OPTION_NOT_FOUND + optionId);
        }

        if (option.getQuantity() < quantity) {
            throw new IllegalArgumentException(INSUFFICIENT_QUANTITY + optionId);
        }

        option.subtractQuantity(quantity);
        optionRepository.save(option);
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
