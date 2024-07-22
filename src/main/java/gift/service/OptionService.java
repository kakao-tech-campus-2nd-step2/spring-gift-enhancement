package gift.service;

import gift.dto.OptionRequestDto;
import gift.repository.OptionRepository;
import gift.vo.Option;
import gift.vo.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    private Product getProduct(OptionRequestDto optionRequestDto) {
        return productService.getProductById(optionRequestDto.productId());
    }

    private Option getOption(Long optionId) {
        return optionRepository.findById(optionId).orElseThrow(() -> new NoSuchElementException("해당 옵션을 찾을 수 없습니다. "));
    }

    public List<Option> getOptionsPerProduct(Long id) {
        return optionRepository.findAllByProductId(id).orElseThrow(
                () -> new IllegalArgumentException("해당 상품의 옵션이 존재하지 않습니다."));
    }

    public void addOption(OptionRequestDto optionRequestDto) {
        Option option = optionRequestDto.toOption(getProduct(optionRequestDto));
        optionRepository.save(option);
    }

    public void subtractOptionQuantity(Long optionId, int quantity) {
        Option option = getOption(optionId);
        option.subtractQuantity(quantity);
        optionRepository.save(option);
    }
}
