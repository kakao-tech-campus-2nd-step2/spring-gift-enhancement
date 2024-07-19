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
                                .map(op -> OptionResponse.fromEntity(op))
                                .toList();
        return  responses;
    }

    public OptionResponse addOption(Long productId, OptionRequest newOption) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new RuntimeException("No such product with id" + productId)
        );

        validateOptionName(productId, newOption);

        Option option = new Option(newOption.name(), newOption.quantity(), product);
        optionRepository.save(option);
        return OptionResponse.fromEntity(option);
    }

    private void validateOptionName(Long productId, OptionRequest newOption) {
        String newOptionName = newOption.name();
        getAllOptionsByProductId(productId).stream()
            .map(OptionResponse::getName)
            .anyMatch(name -> {
                if (name.equals(newOptionName)) {
                    throw new RuntimeException("An option with the name " + newOption.name() +
                        " already exists for this product.");
                }
                return false;
            });
    }
}
