package gift.service;

import gift.dto.OptionAddRequest;
import gift.dto.OptionResponse;
import gift.dto.OptionSubtractRequest;
import gift.dto.OptionUpdateRequest;
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

    public OptionResponse addOption(OptionAddRequest optionAddRequest) {
        optionNameValidation(optionAddRequest.productId(), optionAddRequest.name());
        var option = saveOptionWithOptionRequest(optionAddRequest);
        return getOptionResponseFromOption(option);
    }

    public void updateOption(Long id, OptionUpdateRequest optionUpdateRequest) {
        var option = findOptionById(id);
        option.updateOptionInfo(optionUpdateRequest.name(), optionUpdateRequest.quantity());
        optionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId, Pageable pageable) {
        return optionRepository.findAllByProductId(productId, pageable)
                .stream()
                .map(this::getOptionResponseFromOption)
                .toList();
    }

    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    public void makeDefaultOption(Product product) {
        var option = new Option(product, "기본", 1000);
        optionRepository.save(option);
    }

    public void subtractOptionQuantity(Long id, OptionSubtractRequest optionSubtractRequest) {
        var option = optionRepository.findByIdWithLock(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품 옵션이 존재하지 않습니다."));
        subtractValidation(option, optionSubtractRequest.quantity());
        option.subtract(optionSubtractRequest.quantity());
        optionRepository.save(option);
    }

    private Option saveOptionWithOptionRequest(OptionAddRequest optionAddRequest) {
        var product = findProductById(optionAddRequest.productId());
        var option = new Option(product, optionAddRequest.name(), optionAddRequest.quantity());
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

    private void subtractValidation(Option option, Integer count) {
        if (count > option.getQuantity()) {
            throw new BadRequestException("주문량이 옵션의 잔여 갯수를 초과합니다");
        }
    }
}
