package gift.service;

import gift.domain.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.web.dto.request.productoption.CreateProductOptionRequest;
import gift.web.dto.request.productoption.SubtractProductOptionQuantityRequest;
import gift.web.dto.request.productoption.UpdateProductOptionRequest;
import gift.web.dto.response.productoption.CreateProductOptionResponse;
import gift.web.dto.response.productoption.ReadAllProductOptionsResponse;
import gift.web.dto.response.productoption.ReadProductOptionResponse;
import gift.web.dto.response.productoption.SubtractProductOptionQuantityResponse;
import gift.web.dto.response.productoption.UpdateProductOptionResponse;
import gift.web.validation.exception.client.AlreadyExistsException;
import gift.web.validation.exception.client.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    public ProductOptionService(ProductOptionRepository productOptionRepository) {
        this.productOptionRepository = productOptionRepository;
    }

    @Transactional
    public CreateProductOptionResponse createOption(Long productId, CreateProductOptionRequest request) {
        String optionName = request.getName();
        validateOptionNameExists(productId, optionName);

        ProductOption createdOption = productOptionRepository.save(request.toEntity(productId));

        return CreateProductOptionResponse.fromEntity(createdOption);
    }

    /**
     * 상품 옵션 이름이 이미 존재하는지 확인합니다.<br>
     * 이미 존재한다면 {@link AlreadyExistsException} 을 발생시킵니다.
     * @param productId 상품 아이디
     * @param name 상품 옵션 이름
     */
    private void validateOptionNameExists(Long productId, String name) {
        validateOptionNameExists(null, productId, name);
    }

    /**
     * 상품 옵션 이름이 이미 존재하는지 확인합니다.<br>
     * 이미 존재한다면 {@link AlreadyExistsException} 을 발생시킵니다.
     * @param optionId 현재 상품 옵션 아이디 (옵션 새로 생성 시 null)
     * @param productId 상품 아이디
     * @param name 상품 옵션 이름
     */
    private void validateOptionNameExists(Long optionId, Long productId, String name) {
        if(optionId == null) {
            optionId = -1L;
        }
        productOptionRepository.findDuplicatedProductOption(optionId, productId, name)
            .ifPresent(duplicatedOptionId -> {
                throw new AlreadyExistsException(name);
            });
    }

    public ReadAllProductOptionsResponse readAllOptions(Long productId) {
        List<ReadProductOptionResponse> options = productOptionRepository.findAllByProductId(productId)
            .stream()
            .map(ReadProductOptionResponse::fromEntity)
            .toList();
        return ReadAllProductOptionsResponse.from(options);
    }

    @Transactional
    public UpdateProductOptionResponse updateOption(Long optionId, Long productId, UpdateProductOptionRequest request) {
        String optionName = request.getName();
        validateOptionNameExists(optionId, productId, optionName);

        ProductOption option = productOptionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException("상품 옵션", optionId.toString()));

        ProductOption updateParam = request.toEntity();
        option.update(updateParam);

        return UpdateProductOptionResponse.fromEntity(option);
    }

    @Transactional
    public SubtractProductOptionQuantityResponse subtractOptionStock(Long optionId, SubtractProductOptionQuantityRequest request) {
        ProductOption option = productOptionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException("상품 옵션", optionId.toString()));

        option.subtractQuantity(request.getQuantity());

        return SubtractProductOptionQuantityResponse.fromEntity(option);
    }

    @Transactional
    public void deleteOption(Long optionId) {
        ProductOption option = productOptionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException("상품 옵션", optionId.toString()));

        productOptionRepository.delete(option);
    }

    @Transactional
    public void deleteAllOptionsByProductId(Long productId) {
        productOptionRepository.deleteAllByProductId(productId);
    }
}
