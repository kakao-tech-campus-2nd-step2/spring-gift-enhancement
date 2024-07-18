package gift.product.service;

import gift.product.model.OptionRepository;
import gift.product.model.dto.option.CreateOptionRequest;
import gift.product.model.dto.option.Option;
import gift.product.model.dto.option.UpdateOptionRequest;
import gift.product.model.dto.product.Product;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    @Transactional
    public void addOption(CreateOptionRequest createOptionRequest) {
        Product product = productService.findProduct(createOptionRequest.productId());
        Option option = new Option(createOptionRequest.name(), createOptionRequest.quantity(),
                createOptionRequest.additionalCost(),
                product);
        optionRepository.save(option);
    }

    @Transactional
    public void updateOption(Long optionId, UpdateOptionRequest updateOptionRequest) {
        Option option = getOption(optionId);
        option.setName(updateOptionRequest.name());
        option.setQuantity(updateOptionRequest.quantity());
        option.setAdditionalCost(updateOptionRequest.additionalCost());
        optionRepository.save(option);
    }

    @Transactional
    public void deleteOption(Long optionId) {
        Optional<Option> optionOptional = optionRepository.findByIdAndIsActiveTrue(optionId);
        Option existingOption = optionOptional.orElseThrow(() -> new EntityNotFoundException("Option"));
        existingOption.setActive(false);
        optionRepository.save(existingOption);
    }

    public Option getOption(Long optionId) {
        Optional<Option> option = optionRepository.findByIdAndIsActiveTrue(optionId);
        return option.orElseThrow(() -> new EntityNotFoundException("Category"));
    }
}
