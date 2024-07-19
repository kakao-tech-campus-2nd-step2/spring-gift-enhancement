package gift.service;

import gift.repository.ProductOptionRepository;
import gift.web.dto.response.productoption.ReadAllProductOptionsResponse;
import gift.web.dto.response.productoption.ReadProductOptionResponse;
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

    public ReadAllProductOptionsResponse readAllOptions(Long productId) {
        List<ReadProductOptionResponse> options = productOptionRepository.findAllByProductId(productId)
            .stream()
            .map(ReadProductOptionResponse::fromEntity)
            .toList();
        return ReadAllProductOptionsResponse.from(options);
    }
}
