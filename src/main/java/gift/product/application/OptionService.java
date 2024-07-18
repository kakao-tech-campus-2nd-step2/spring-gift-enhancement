package gift.product.application;

import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.product.dao.OptionRepository;
import gift.product.dao.ProductRepository;
import gift.product.dto.OptionRequest;
import gift.product.dto.OptionResponse;
import gift.product.entity.Product;
import gift.product.util.OptionMapper;
import gift.product.util.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OptionService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public Set<OptionResponse> getProductOptionsByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponseDto)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND))
                .options();
    }

    @Transactional
    public OptionResponse addOptionToProduct(Long id, OptionRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return OptionMapper.toResponseDto(
                optionRepository.save(OptionMapper.toEntity(request, product))
        );
    }

    @Transactional
    public void deleteOptionFromProduct(Long id, OptionRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getOptions()
                .size() == 1) {
            throw new CustomException(ErrorCode.OPTION_REMOVE_FAILED);
        }

        optionRepository.deleteByProduct_IdAndName(id, request.name());
    }

}
