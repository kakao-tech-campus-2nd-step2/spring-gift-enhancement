package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.exception.BadRequestException;
import gift.exception.DuplicatedNameException;
import gift.exception.NotFoundElementException;
import gift.model.Product;
import gift.model.ProductOption;
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

    public ProductOptionResponse addOption(Long productId, ProductOptionRequest productOptionRequest) {
        optionNameValidation(productId, productOptionRequest.name());
        var productOption = saveOptionWithOptionRequest(productId, productOptionRequest);
        return getOptionResponseFromOption(productOption);
    }

    public void updateOption(Long productId, Long id, ProductOptionRequest productOptionRequest) {
        var productOption = findOptionById(id);
        if (!productOption.getProduct().getId().equals(productId)) {
            throw new BadRequestException("잘못된 상품 옵션 수정 요청입니다.");
        }
        productOption.updateOptionInfo(productOptionRequest.name(), productOptionRequest.quantity());
        productOptionRepository.save(productOption);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> getOptions(Long productId, Pageable pageable) {
        return productOptionRepository.findAllByProductId(productId, pageable)
                .stream()
                .map(this::getOptionResponseFromOption)
                .toList();
    }

    public void deleteOption(Long productId, Long id) {
        var product = findProductById(productId);
        var productOption = findOptionById(id);
        product.getProductOptionList().remove(productOption);
        productOptionRepository.deleteById(id);
    }

    private ProductOption saveOptionWithOptionRequest(Long productId, ProductOptionRequest productOptionRequest) {
        var product = findProductById(productId);
        var productOption = new ProductOption(product, productOptionRequest.name(), productOptionRequest.quantity());
        return productOptionRepository.save(productOption);
    }

    private ProductOptionResponse getOptionResponseFromOption(ProductOption productOption) {
        return ProductOptionResponse.of(productOption.getId(), productOption.getName(), productOption.getQuantity());
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품이 존재하지 않습니다."));
    }

    private ProductOption findOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품 옵션이 존재하지 않습니다."));
    }

    private void optionNameValidation(Long productId, String name) {
        if (productOptionRepository.existsProductOptionByProductIdAndName(productId, name)) {
            throw new DuplicatedNameException("이미 존재하는 상품의 상품 옵션입니다.");
        }
    }
}
