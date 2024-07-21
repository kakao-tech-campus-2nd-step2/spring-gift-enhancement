package gift.service;

import gift.DTO.option.OptionRequest;
import gift.DTO.option.OptionResponse;
import gift.domain.Option;
import gift.domain.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;
    @Autowired
    public OptionService(
        OptionRepository optionRepository,
        ProductRepository productRepository
    ) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getAllOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("No such product with id" + productId));

        List<OptionResponse> responses = product.getOptions().stream()
                                .map(OptionResponse::fromEntity)
                                .toList();
        return  responses;
    }

    @Transactional
    public OptionResponse addOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new RuntimeException("No such product with id" + productId)
        );

        validateOptionName(productId, optionRequest);

        Option option = new Option(optionRequest.name(), optionRequest.quantity(), product);
        product.addOption(option);
        optionRepository.save(option);
        return OptionResponse.fromEntity(option);
    }

    @Transactional
    public OptionResponse decrementOptionQuantity(Long optionId, Long quantity) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new RuntimeException("No such option with id " + optionId));

        option.subtract(quantity);
        Option savedOption = optionRepository.save(option);
        return OptionResponse.fromEntity(savedOption);
    }

    private void validateOptionName(Long productId, OptionRequest newOption) {
        String newOptionName = newOption.name();
        getAllOptionsByProductId(productId).stream()
            .map(OptionResponse::getName)
            .anyMatch(name -> {
                if (name.equals(newOptionName)) {
                    throw new RuntimeException("An option with the name " + newOptionName +
                        " already exists for this product.");
                }
                return false;
            });
    }
}
