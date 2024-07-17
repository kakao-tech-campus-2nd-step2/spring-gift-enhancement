package gift.service;

import gift.dto.ProductOptionRequestDto;
import gift.dto.ProductOptionResponseDto;
import gift.entity.Option;
import gift.entity.OptionName;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.mapper.ProductOptionMapper;
import gift.repository.OptionRepository;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductRepository productRepository, OptionRepository optionRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public ProductOptionResponseDto addProductOption(ProductOptionRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + requestDto.getProductId()));
        Option option = optionRepository.findById(requestDto.getOptionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.OPTION_NOT_FOUND, "ID: " + requestDto.getOptionId()));

        boolean isDuplicateOption = productOptionRepository.existsByProductAndOptionName(product, option.getName());
        if (isDuplicateOption) {
            throw new BusinessException(ErrorCode.DUPLICATE_OPTION, " 옵션 : " + option.getName().getValue());
        }

        ProductOption productOption = new ProductOption(product, option, requestDto.getQuantity());
        ProductOption savedProductOption = productOptionRepository.save(productOption);

        return ProductOptionMapper.toProductOptionResponseDto(savedProductOption);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponseDto> getProductOptions(Long productId) {
        Product product = getProductById(productId);
        List<ProductOption> productOptions = productOptionRepository.findAll().stream()
                .filter(po -> po.getProduct().equals(product))
                .collect(Collectors.toList());
        return productOptions.stream()
                .map(ProductOptionMapper::toProductOptionResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductOptionResponseDto updateProductOption(Long id, ProductOptionRequestDto productOptionRequestDto) {
        ProductOption productOption = getProductOptionById(id);
        productOption.updateQuantity(productOptionRequestDto.getQuantity());
        productOptionRepository.save(productOption);
        return ProductOptionMapper.toProductOptionResponseDto(productOption);
    }

    @Transactional
    public void deleteProductOption(Long id) {
        ProductOption productOption = getProductOptionById(id);
        productOptionRepository.delete(productOption);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id));
    }

    private Option getOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.OPTION_NOT_FOUND, "ID: " + id));
    }

    private ProductOption getProductOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND, "ID: " + id));
    }
}
