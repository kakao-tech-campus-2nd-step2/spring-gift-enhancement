package gift.product.service;

import gift.product.domain.ProductOption;
import gift.product.exception.ProductNotFoundException;
import gift.product.exception.ProductOptionDuplicatedException;
import gift.product.exception.ProductOptionNotDeletedException;
import gift.product.exception.ProductOptionNotFoundException;
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
        validateDuplicatedProductName(productId, command.name());

        var productOption = command.toEntity(product);

        var savedProduct = productOptionRepository.save(productOption);
        return savedProduct.getId();
    }

    @Transactional
    public void modifyProductOption(Long productId, Long optionId,
                                    ProductOptionCommand productOptionCommand) {
        var productOption = getExistsProductOption(productId, optionId);

        productOption.modify(productOptionCommand.name(), productOptionCommand.quantity());
    }

    @Transactional(readOnly = true)
    public ProductOptionInfo getProductOptionInfo(Long productId, Long optionId) {
        var productOption = getExistsProductOption(productId, optionId);

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

    @Transactional
    public void deleteProductOption(Long productId, Long optionId) {
        var productOption = productOptionRepository.findByProductId(productId);
        if (productOption.size() == 1) {
            throw ProductOptionNotDeletedException.of(productId, optionId);
        }

        var option = getExistsProductOption(productId, optionId);
        productOptionRepository.delete(option);
    }

    private ProductOption getExistsProductOption(Long productId, Long optionId) {
        var option = productOptionRepository.findByProductIdAndId(productId, optionId)
                .orElseThrow(() -> ProductOptionNotFoundException.of(productId, optionId));

        return option;
    }

    private void validateDuplicatedProductName(Long productId, String name) {
        var productOption = productOptionRepository.findByProductId(productId);

        if (productOption.stream().anyMatch(option -> option.isSameName(name))) {
            throw ProductOptionDuplicatedException.of(productId, name);
        }
    }
}
