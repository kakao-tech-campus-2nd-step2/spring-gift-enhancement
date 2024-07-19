package gift.product.business.service;

import gift.product.business.dto.OptionRegisterDto;
import gift.product.persistence.repository.OptionRepository;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public List<Long> createOption(List<OptionRegisterDto> optionRegisterDtos, Long productId) {
        var product = productRepository.getReferencedProduct(productId);
        var options = optionRegisterDtos.stream()
            .map(optionRegisterDto -> optionRegisterDto.toOption(product))
            .toList();
        return optionRepository.saveAll(options);
    }
}
