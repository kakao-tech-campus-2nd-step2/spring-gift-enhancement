package gift.domain.product.service;

import gift.domain.product.dto.OptionDto;
import gift.domain.product.entity.Option;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.OptionJpaRepository;
import gift.domain.product.repository.ProductJpaRepository;
import gift.exception.InvalidProductInfoException;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public OptionService(OptionJpaRepository optionJpaRepository, ProductJpaRepository productJpaRepository) {
        this.optionJpaRepository = optionJpaRepository;
        this.productJpaRepository = productJpaRepository;

    }

    public OptionDto create(long productId, OptionDto optionDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        Option option = optionDto.toOption(product);

        product.addOption(option);
        productJpaRepository.save(product);
        return OptionDto.from(option);
    }
}
