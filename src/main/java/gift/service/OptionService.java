package gift.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDto;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDto> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findAllByProductId(productId);

        return options.stream()
            .map(OptionDto::convertToDto)
            .collect(Collectors.toList());
    }

    public OptionDto saveOption(Long productId , OptionDto optionDto) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("해당 id의 상품 없음: " + productId));
        List<Option> options = optionRepository.findAllByProductId(productId);
        for(Option option: options) {
            if(option.getName().equals(optionDto.getName())){
                throw new IllegalStateException("옵션에 중복 이름 안됨");
            }
        }
        Option newOption = new Option(optionDto.getName(), optionDto.getQuantity(), product);
        Option savedOption = optionRepository.save(newOption);
        return new OptionDto(savedOption.getId(), savedOption.getName(), savedOption.getQuantity());
    }
}
