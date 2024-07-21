package gift.service.option;

import gift.domain.option.Option;
import gift.domain.option.OptionRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.mapper.OptionMappper;
import gift.web.dto.OptionDto;
import gift.web.exception.OptionNotFoundException;
import gift.web.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final ProductRepository productRepository;
    private OptionRepository optionRepository;
    private OptionMappper optionMapper;

    public OptionService(OptionRepository optionRepository, OptionMappper optionMapper,
        ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.optionMapper = optionMapper;
        this.productRepository = productRepository;
    }

    public OptionDto createOption(Long productId, OptionDto optionDto) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("제품이 없슴다."));

        Option option = optionRepository.save(optionMapper.toEntity(optionDto, product));
        return optionMapper.toDto(option);
    }

    public List<OptionDto> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("제품이 없슴다."));

        return optionRepository.findAllByProductId(productId)
            .stream()
            .map(optionMapper::toDto)
            .toList();
    }

    public OptionDto updateOption(Long optionId, OptionDto optionDto) {
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException("옵션이 없슴다."));

        option.updateOption(optionDto.name(), optionDto.quantity());

        return optionMapper.toDto(option);
    }
}
