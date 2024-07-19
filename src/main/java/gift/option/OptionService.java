package gift.option;

import gift.common.exception.OptionException;
import gift.common.exception.ProductException;
import gift.option.model.Option;
import gift.option.model.OptionRequest;
import gift.option.model.OptionResponse;
import gift.option.model.Options;
import gift.product.ProductErrorCode;
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
            .stream()
            .map(OptionResponse::from)
            .toList();
    }

    @Transactional
    public Long addOption(Long productId, OptionRequest optionRequest)
        throws ProductException, OptionException {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException(
                ProductErrorCode.NOT_FOUND));
        Options options = new Options(optionRepository.findAllByProductId(productId));
        Option option = new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
        options.validate(option);
        option = optionRepository.save(option);
        return option.getId();
    }

    @Transactional
    public void updateOption(Long optionId, OptionRequest optionRequest) throws OptionException {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        option.updateInfo(optionRequest.getName(), optionRequest.getQuantity());
    }

    @Transactional
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
