package gift.option;

import gift.product.ProductRepository;
import gift.product.model.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private OptionRepository optionRepository;
    private ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptions(Long productId, Pageable pageable) {
        return optionRepository.findAllByProductId(productId, pageable)
            .map(OptionResponse::from).getContent();
    }

    public Long addOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId).orElseThrow();
        Options options = optionRepository.findAllByProductId(productId);
        Option option = new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
        options.validate(option);
        option = optionRepository.save(option);
        return option.getId();
    }
}
