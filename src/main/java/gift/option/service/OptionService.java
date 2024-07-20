package gift.option.service;

import gift.option.dto.OptionDto;
import gift.option.model.Option;
import gift.option.repository.OptionRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDto> getOptionsByProductId(Long id) {
        return optionRepository.findByProductId(id).stream()
                .map(option -> new OptionDto(option.getId(), option.getName(), option.getQuantity()))
                .collect(Collectors.toList());
    }

    public OptionDto addOptionToProduct(Long id, OptionDto optionDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        Option option = new Option(product, optionDto.getName(), optionDto.getQuantity());
        Option savedOption = optionRepository.save(option);

        return new OptionDto(savedOption.getId(), savedOption.getName(), savedOption.getQuantity());
    }

    private void validateOptionName(String name) {
        if (name.length() > 50) {
            throw new IllegalArgumentException("옵션명은 공백 포함 최대 15자 가능합니다.");
        }
        String pattern = "[\\w\\s\\(\\)\\[\\]+&/-]*";
        if (!name.matches(pattern)) {
            throw new IllegalArgumentException("옳지 않은 문자가 사용되었습니다.");
        }
    }
}
