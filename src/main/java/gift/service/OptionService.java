package gift.service;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public OptionResponse addOption(Long productId, @Valid OptionRequest optionRequest) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Option option = new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
        Option savedOption = optionRepository.save(option);
        return OptionResponse.from(savedOption);
    }

    public List<OptionResponse> getOptions(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream()
            .map(OptionResponse::from)
            .collect(Collectors.toList());
    }

}
