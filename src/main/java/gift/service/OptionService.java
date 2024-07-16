package gift.service;

import gift.controller.dto.OptionRequest;
import gift.controller.dto.OptionResponse;
import gift.domain.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.utils.error.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    OptionRepository optionRepository;
    ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public OptionResponse addOption(OptionRequest optionRequest){
        Product product = productRepository.findById(optionRequest.getProductId()).orElseThrow(
            () -> new ProductNotFoundException("Product Not Found")
        );
    }
}
