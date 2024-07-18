package gift.api.option;

import gift.api.product.Product;
import gift.api.product.ProductRepository;
import gift.global.exception.NoSuchEntityException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
            .stream()
            .map(OptionResponse::of)
            .collect(Collectors.toList());
    }

    public void add(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchEntityException("product"));
        Option option = optionRequest.toEntity(product);
        Options.of(optionRepository.findAllByProductId(productId))
            .validate(option);
        optionRepository.save(option);
    }
}
