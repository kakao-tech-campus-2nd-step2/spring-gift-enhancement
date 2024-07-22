package gift.service;

import gift.dto.OptionDto;
import gift.repository.OptionRepository;
import gift.vo.Option;
import gift.vo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    private Product getProduct(OptionDto optionDto) {
        return productService.getProductById(optionDto.productId());
    }

    public List<Option> getOptionsPerProduct(Long id) {
        return optionRepository.findAllByProductId(id).orElseThrow(
                () -> new IllegalArgumentException("해당 상품의 옵션이 존재하지 않습니다."));
    }

    public void addOption(OptionDto optionDto) {
        Option option = optionDto.toOption(getProduct(optionDto));
        optionRepository.save(option);
    }
}
