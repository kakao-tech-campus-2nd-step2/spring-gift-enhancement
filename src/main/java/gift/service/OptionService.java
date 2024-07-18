package gift.service;

import gift.common.exception.OptionNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.model.option.Option;
import gift.model.option.OptionRequest;
import gift.model.option.OptionResponse;
import gift.model.product.Product;
import gift.repository.option.OptionRepository;
import gift.repository.product.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;
    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptionByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ProductNotFoundException("해당 Id의 상품은 존재하지 않습니다.")
        );
        return product.getOptions().stream()
            .map(OptionResponse::from)
            .collect(Collectors.toList());
    }

    public OptionResponse addOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId).orElseThrow();
        Option option = toEntity(optionRequest, product);
        if(optionRepository.existsByProductAndName(product, optionRequest.name())) {
            throw new IllegalArgumentException("같은 상품 내 옵션 이름은 중복될 수 없습니다.");
        }
        Option savedOption = optionRepository.save(option);
        return OptionResponse.from(savedOption);
    }

    public OptionResponse updateOption(Long productId, Long optionId, OptionRequest optionRequest) {
        Option option = optionRepository.findById(optionId).orElseThrow(
            () -> new OptionNotFoundException("해당 Id의 옵션은 존재하지 않습니다.")
        );
        option.update(optionRequest.name(), optionRequest.quantity());
        return OptionResponse.from(option);
    }
    public void deleteOption(Long productId,Long optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow(
            () -> new OptionNotFoundException("해당 Id의 옵션은 존재하지 않습니다.")
        );
        optionRepository.delete(option);
    }

    public Option toEntity(OptionRequest optionRequest, Product product) {
        return new Option(optionRequest.name(), optionRequest.quantity(), product);
    }
}
