package gift.service.product;

import gift.global.validate.NotFoundException;
import gift.model.product.Option;
import gift.model.product.Options;
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

    @Transactional
    public List<OptionModel.Info> createOption(Long productId, OptionCommand.Register command) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        List<Option> optionList = command.toEntities(product);
        Options.validateOptions(optionList);
        Options options = new Options(optionRepository.findByProductId(productId));

        for (Option option : optionList) {
            options.validateUniqueName(option);
            optionRepository.save(option);
        }
        return optionList.stream().map(OptionModel.Info::from).toList();
    }

    @Transactional(readOnly = true)
    public List<OptionModel.Info> getOptions(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream().map(OptionModel.Info::from).toList();
    }

    @Transactional
    public OptionModel.Info updateOption(Long optionId, Long productId,
        OptionCommand.Update command) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        //수정한 이름과 원래 이름이 같음
        if (option.isSameName(command.name())) {
            return OptionModel.Info.from(option);
        }
        Options options = new Options(optionRepository.findByProductId(productId));
        options.validateUniqueName(option);
        option.update(command.name(), command.quantity());
        return OptionModel.Info.from(option);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        if (!optionRepository.existsById(optionId)) {
            throw new NotFoundException("Option not found");
        }
        Options options = new Options(optionRepository.findByProductId(productId));
        if (!options.isDeletePossible()) {
            throw new IllegalArgumentException("Option이 1개 일때는 삭제할 수 없습니다.");
        }
        optionRepository.deleteById(optionId);
    }

    public synchronized void puchaseOption(Long optionId, Integer quantity) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        option.purchase(quantity);
        optionRepository.save(option);
    }

    @Transactional
    public Option findOptionById(Long optionId) {
        return optionRepository.findById(optionId)
            .orElseThrow(() -> new NotFoundException("Option not found"));
    }
}
