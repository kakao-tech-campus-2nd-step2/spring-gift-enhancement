package gift.service;

import gift.domain.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.web.dto.request.productoption.CreateProductOptionRequest;
import gift.web.dto.response.productoption.CreateProductOptionResponse;
import gift.web.dto.response.productoption.ReadAllProductOptionsResponse;
import gift.web.dto.response.productoption.ReadProductOptionResponse;
import gift.web.validation.exception.client.AlreadyExistsException;
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
     * 이미 존재한다면 BadRequestException을 발생시킵니다.
     * @param productId 상품 ID
     * @param name 상품 옵션 이름
     */
    private void validateOptionNameExists(Long productId, String name) {
        boolean isExistsOptionName = productOptionRepository.existsByNameAndProductId(name, productId);
        if (isExistsOptionName) {
            throw new AlreadyExistsException(name);
        }
    }

    public ReadAllProductOptionsResponse readAllOptions(Long productId) {
        List<ReadProductOptionResponse> options = productOptionRepository.findAllByProductId(productId)
            .stream()
            .map(ReadProductOptionResponse::fromEntity)
            .toList();
        return ReadAllProductOptionsResponse.from(options);
    }


}
