package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.BusinessException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponseDto> getAllOptions() {
        List<Option> options = optionRepository.findAll();

        return options.stream()
            .map(OptionResponseDto::new)
            .collect(Collectors.toList());
    }

    public List<OptionResponseDto> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("해당 id의 상품이 존재하지 않습니다."));

        return product.getOptions().stream().map(OptionResponseDto::new)
            .collect(Collectors.toList());
    }

    public void addOptionToProduct(Long productId, OptionRequestDto request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("해당 id의 상품이 존재하지 않습니다."));

        for (Option option : product.getOptions()) {
            if (option.getName().equals(request.getName())) {
                throw new BusinessException("같은 상품 내에는 동일한 옵션 이름을 가질 수 없습니다.");
            }
        }

        var option = new Option(request.getName(), request.getQuantity(), product);
        optionRepository.save(option);
    }
}