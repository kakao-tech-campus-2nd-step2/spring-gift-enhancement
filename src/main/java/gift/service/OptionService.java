package gift.service;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.dto.ProductResponse;
import gift.exception.BadRequestException;
import gift.exception.DuplicatedNameException;
import gift.exception.NotFoundElementException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;


    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public OptionResponse addOption(Long productId, OptionRequest optionRequest) {
        optionNameValidation(productId, optionRequest.name());
        var option = saveOptionWithOptionRequest(productId, optionRequest);
        return getOptionResponseFromOption(option);
    }

    public void updateOption(Long productId, Long id, OptionRequest optionRequest) {
        var option = findOptionById(id);
        if (!option.getProduct().getId().equals(productId)) {
            throw new BadRequestException("잘못된 상품 옵션 수정 요청입니다.");
        }
        option.updateOptionInfo(optionRequest.name(), optionRequest.quantity());
        optionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId, Pageable pageable) {
        return optionRepository.findAllByProductId(productId, pageable)
                .stream()
                .map(this::getOptionResponseFromOption)
                .toList();
    }

    public void deleteOption(Long productId, Long id) {
        var option = findOptionById(id);
        if (!option.getProduct().getId().equals(productId)) {
            throw new BadRequestException("잘못된 상품 옵션에 대한 요청입니다.");
        }
        optionRepository.deleteById(id);
    }

    public void makeDefaultOption(ProductResponse productResponse) {
        var product = productRepository.findById(productResponse.id())
                .orElseThrow(() -> new NotFoundElementException(productResponse.id() + "를 가진 상품이 존재하지 않습니다."));
        var option = new Option(product, "기본", 1000);
        optionRepository.save(option);
    }

    private Option saveOptionWithOptionRequest(Long productId, OptionRequest optionRequest) {
        var product = findProductById(productId);
        var option = new Option(product, optionRequest.name(), optionRequest.quantity());
        return optionRepository.save(option);
    }

    private OptionResponse getOptionResponseFromOption(Option option) {
        return OptionResponse.of(option.getId(), option.getName(), option.getQuantity());
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품이 존재하지 않습니다."));
    }

    private Option findOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품 옵션이 존재하지 않습니다."));
    }

    private void optionNameValidation(Long productId, String name) {
        if (optionRepository.existsOptionByProductIdAndName(productId, name)) {
            throw new DuplicatedNameException("이미 존재하는 상품의 상품 옵션입니다.");
        }
    }
}
