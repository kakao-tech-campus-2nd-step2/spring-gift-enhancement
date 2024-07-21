package gift.option;

import gift.common.exception.OptionException;
import gift.common.exception.ProductException;
import gift.option.model.Option;
import gift.option.model.OptionRequest;
import gift.option.model.OptionResponse;
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
            .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));
        Option option = new Option(optionRequest.name(), optionRequest.quantity(), product);
        Option.Validator.validateName(optionRepository.findAllByProductId(productId), option);
        optionRepository.save(option);
        return option.getId();
    }

    @Transactional
    public void updateOption(Long optionId, OptionRequest optionRequest) throws OptionException {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        Option.Validator.validateName(
            optionRepository.findAllByProductId(option.getProduct().getId()), option);
        option.updateInfo(optionRequest.name(), optionRequest.quantity());
    }

    @Transactional
    public void deleteOption(Long optionId) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        Option.Validator.validateOptionCount(
            optionRepository.findAllByProductId(option.getProduct().getId()));
        optionRepository.deleteById(optionId);
    }

    @Transactional
    public void subtractOption(Long optionId, OptionRequest optionRequest) throws OptionException {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        option.subtract(optionRequest.quantity());
    }
}
