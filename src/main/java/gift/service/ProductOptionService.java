package gift.service;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.exception.BadRequestException;
import gift.exception.DuplicatedNameException;
import gift.exception.NotFoundElementException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;


    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductRepository productRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
    }

    public OptionResponse addOption(Long productId, OptionRequest optionRequest) {
        optionNameValidation(productId, optionRequest.name());
        var productOption = saveOptionWithOptionRequest(productId, optionRequest);
        return getOptionResponseFromOption(productOption);
    }

    public void updateOption(Long productId, Long id, OptionRequest optionRequest) {
        var productOption = findOptionById(id);
        if (!productOption.getProduct().getId().equals(productId)) {
            throw new BadRequestException("잘못된 상품 옵션 수정 요청입니다.");
        }
        productOption.updateOptionInfo(optionRequest.name(), optionRequest.quantity());
        productOptionRepository.save(productOption);
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId, Pageable pageable) {
        return productOptionRepository.findAllByProductId(productId, pageable)
                .stream()
                .map(this::getOptionResponseFromOption)
                .toList();
    }

    public void deleteOption(Long productId, Long id) {
        var option = findOptionById(id);
        if (!option.getProduct().getId().equals(productId)) {
            throw new BadRequestException("잘못된 상품 옵션에 대한 요청입니다.");
        }
        productOptionRepository.deleteById(id);
    }

    private Option saveOptionWithOptionRequest(Long productId, OptionRequest optionRequest) {
        var product = findProductById(productId);
        var productOption = new Option(product, optionRequest.name(), optionRequest.quantity());
        return productOptionRepository.save(productOption);
    }

    private OptionResponse getOptionResponseFromOption(Option option) {
        return OptionResponse.of(option.getId(), option.getName(), option.getQuantity());
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품이 존재하지 않습니다."));
    }

    private Option findOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품 옵션이 존재하지 않습니다."));
    }

    private void optionNameValidation(Long productId, String name) {
        if (productOptionRepository.existsProductOptionByProductIdAndName(productId, name)) {
            throw new DuplicatedNameException("이미 존재하는 상품의 상품 옵션입니다.");
        }
    }
}
