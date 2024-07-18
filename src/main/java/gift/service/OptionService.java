package gift.service;

import gift.dto.OptionDto;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        return product.getOptions();
    }

    public Option addOption(Long productId, OptionDto optionDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        Option option = new Option(optionDto.getName(), optionDto.getQuantity());
        optionRepository.save(option);//? 이거 있어야하나
        product.getOptions().add(option);
        productRepository.save(product);
        return option;
    }

    public Option updateOption(Long optionId, OptionDto optionDto) {
        optionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 옵션입니다."));
        Option updatedOption = new Option(optionDto.getName(), optionDto.getQuantity());
        return optionRepository.save(updatedOption);
    }

    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
