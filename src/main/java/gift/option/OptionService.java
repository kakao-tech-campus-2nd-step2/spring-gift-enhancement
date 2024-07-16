package gift.option;

import gift.product.Product;
import gift.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> findAllProductOptions(Long id) {
        return optionRepository.findAllByProductId(id).stream().map(OptionResponse::new).toList();
    }

    public OptionResponse insertProductNewOption(Long id, OptionRequest optionRequest) {
        Product product = productRepository.findById(id).orElseThrow();
        Option option = new Option(optionRequest, product);
        product.addOptions(option);
        return new OptionResponse(optionRepository.save(option));
    }

    @Transactional
    public OptionResponse updateOption(Long id, Long optionId, OptionRequest optionRequest) {
        Product product = productRepository.findById(id).orElseThrow();
        Option option = optionRepository.findById(optionId).orElseThrow();
        product.removeOption(option);
        option.updateOption(optionRequest);
        product.addOptions(option);
        return new OptionResponse(option);
    }

    public void deleteOption(Long id, Long optionId) {
        Product product = productRepository.findById(id).orElseThrow();
        product.removeOption(optionRepository.findById(optionId).orElseThrow());
        optionRepository.deleteById(optionId);

    }
}
