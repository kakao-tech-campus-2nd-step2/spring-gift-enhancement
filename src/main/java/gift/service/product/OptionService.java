package gift.service.product;

import gift.global.validate.NotFoundException;
import gift.model.product.Product;
import gift.repository.product.OptionRepository;
import gift.repository.product.ProductRepository;
import gift.service.product.dto.OptionCommand;
import gift.service.product.dto.OptionModel;
import java.util.List;
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

    public OptionModel.Info createOption(Long productId, OptionCommand.Register command) {
        var product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        var option = optionRepository.save(command.toEntity(product));
        return OptionModel.Info.from(option);
    }

    @Transactional(readOnly = true)
    public List<OptionModel.Info> getOptions(Long productId) {
        var options = optionRepository.findByProductId(productId);
        return options.stream().map(OptionModel.Info::from).toList();
    }

    @Transactional
    public OptionModel.Info updateOption(Long optionId, OptionCommand.Update command) {
        var option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        option.update(command.name(), command.quantity());
        return OptionModel.Info.from(option);
    }

    @Transactional
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }

}
