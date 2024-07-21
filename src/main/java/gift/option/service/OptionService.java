package gift.option.service;

import gift.option.dto.OptionDto;
import gift.option.model.Option;
import gift.option.repository.OptionRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public List<OptionDto> getOptionsByProductId(Long id) {
        return optionRepository.findByProductId(id).stream()
                .map(option -> new OptionDto(option.getId(), option.getName(), option.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public OptionDto addOptionToProduct(Long id, OptionDto optionDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        validateOptionName(optionDto.getName());
        validateOptionQuantity(optionDto.getQuantity());

        Option option = new Option(product, optionDto.getName(), optionDto.getQuantity());
        Option savedOption = optionRepository.save(option);

        return new OptionDto(savedOption.getId(), savedOption.getName(), savedOption.getQuantity());
    }

    @Transactional
    public void validateOptionName(String optionName) {
        if (!Pattern.matches("^[\\w\\s\\(\\)\\[\\]+\\-&/_]*$", optionName)) {
            throw new IllegalArgumentException("옵션명에 잘못된 문자가 있습니다.");
        }
    }

    @Transactional
    public void validateOptionQuantity(int quantity) {
        if (quantity < 1 || quantity >= 100000000) {
            throw new IllegalArgumentException("옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다.");
        }
    }

}
