package gift.service.product;

import gift.global.validate.NotFoundException;
import gift.model.product.Option;
import gift.model.product.Options;
import gift.model.product.Product;
import gift.repository.product.OptionRepository;
import gift.repository.product.ProductRepository;
import gift.service.product.dto.OptionCommand;
import gift.service.product.dto.OptionCommand.RegisterMany;
import gift.service.product.dto.OptionModel;
import java.util.List;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public List<OptionModel.Info> createOption(Long productId, RegisterMany command) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        Options options = command.toOptions(product);
        var originOptions = optionRepository.findAllByProductId(productId);

        Options mergedOptions = options.merge(originOptions);

        optionRepository.saveAllByOptions(options);

        return mergedOptions.getOptions().stream().map(OptionModel.Info::from).toList();
    }

    @Transactional(readOnly = true)
    public List<OptionModel.Info> getOptions(Long productId) {
        Options options = optionRepository.findAllByProductId(productId);
        return options.getOptions().stream().map(OptionModel.Info::from).toList();
    }

    @Transactional
    public OptionModel.Info updateOption(Long optionId, Long productId,
        OptionCommand.Update command) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        Options options = optionRepository.findAllByProductId(productId);

        if (options.isUpdatePossible(option, command.name())) {
            option.update(command.name(), command.quantity());
        }

        return OptionModel.Info.from(option);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        if (!optionRepository.existsById(optionId)) {
            throw new NotFoundException("Option not found");
        }
        Options options = optionRepository.findAllByProductId(productId);
        if (!options.isDeletePossible()) {
            throw new IllegalArgumentException("Option이 1개 일때는 삭제할 수 없습니다.");
        }
        optionRepository.deleteById(optionId);
    }

    @Retryable(
        retryFor = {ObjectOptimisticLockingFailureException.class},
        maxAttempts = 100,
        backoff = @Backoff(delay = 200)
    )
    @Transactional
    public void purchaseOption(Long optionId, Integer quantity) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        option.purchase(quantity);
    }


    @Transactional
    public Option findOptionById(Long optionId) {
        return optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
    }
}
