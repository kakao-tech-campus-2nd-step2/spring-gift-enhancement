package gift.option;

import gift.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private OptionRepository optionRepository;
    private ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
            .map(OptionResponse::from).getContent();
    }
}
