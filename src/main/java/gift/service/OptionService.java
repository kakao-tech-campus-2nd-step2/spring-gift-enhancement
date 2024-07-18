package gift.service;

import static gift.util.constants.OptionConstants.OPTION_NOT_FOUND;
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
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));

        return product.getOptions().stream()
            .map(OptionService::convertToDTO)
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

        Option option = new Option(null, optionCreateRequest.name(), optionCreateRequest.quantity(),
            product);
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

        option.update(optionUpdateRequest.name(), optionUpdateRequest.quantity(), product);
        Option updatedOption = optionRepository.save(option);
        return convertToDTO(updatedOption);
    }

    public void deleteOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND + optionId));

        if (!option.isProductIdMatching(productId)) {
            throw new OptionNotFoundException(OPTION_NOT_FOUND + optionId);
        }

        optionRepository.delete(option);
    }

    // Mapper methods
    private static OptionResponse convertToDTO(Option option) {
        return new OptionResponse(
            option.getId(),
            option.getName(),
            option.getQuantity()
        );
    }
}
