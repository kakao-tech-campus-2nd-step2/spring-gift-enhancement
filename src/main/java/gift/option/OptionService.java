package gift.option;

import gift.product.ProductRepository;
import gift.product.model.Product;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
            .getOptions()
            .stream()
            .map(OptionResponse::from)
            .toList();

    }

    @Transactional
    public Long addOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId).orElseThrow();
        Options options = optionRepository.findAllByProductId(productId);
        Option option = new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
        options.validate(option);
        option = optionRepository.save(option);
        return option.getId();
    }

    @Transactional
    public void updateOption(Long optionId, OptionRequest optionRequest) {
        Option option = optionRepository.findById(optionId).orElseThrow();
        option.updateInfo(optionRequest.getName(), optionRequest.getQuantity());
    }

    @Transactional
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
