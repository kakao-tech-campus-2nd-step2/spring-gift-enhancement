package gift.product.service;

import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductOptionRepository;
import gift.product.persistence.ProductRepository;
import gift.product.service.command.ProductOptionCommand;
import gift.product.service.dto.ProductOptionInfo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOptionService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public ProductOptionService(ProductRepository productRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Transactional
    public Long createProductOption(final Long productId, ProductOptionCommand command) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> ProductNotFoundException.of(productId));
        var productOption = command.toEntity(product);

        var savedProduct = productOptionRepository.save(productOption);
        return savedProduct.getId();
    }

    @Transactional
    public void modifyProductOption(Long productId, Long optionId,
                                    ProductOptionCommand productOptionCommand) {
        var productOption = productOptionRepository.findByProductIdAndId(productId, optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 옵션입니다."));
        productOption.modify(productOptionCommand.name(), productOptionCommand.quantity());
    }

    @Transactional(readOnly = true)
    public ProductOptionInfo getProductOptionInfo(Long productId, Long optionId) {
        var productOption = productOptionRepository.findByProductIdAndId(productId, optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 옵션입니다."));

        return ProductOptionInfo.from(productOption);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionInfo> getAllProductOptions(Long productId) {
        var productOptions = productOptionRepository.findByProductId(productId);

        var response = productOptions.stream()
                .map(ProductOptionInfo::from)
                .toList();
        return response;
    }
}
