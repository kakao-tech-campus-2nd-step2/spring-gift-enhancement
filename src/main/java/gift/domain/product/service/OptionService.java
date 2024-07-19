package gift.domain.product.service;

import gift.domain.product.dto.OptionDto;
import gift.domain.product.entity.Option;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.OptionJpaRepository;
import gift.domain.product.repository.ProductJpaRepository;
import gift.exception.InvalidProductInfoException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public OptionService(OptionJpaRepository optionJpaRepository, ProductJpaRepository productJpaRepository) {
        this.optionJpaRepository = optionJpaRepository;
        this.productJpaRepository = productJpaRepository;

    }

    public void create(Product product, List<OptionDto> optionRequestDtos) {
        for (OptionDto optionDto : optionRequestDtos) {
            Option option = optionDto.toOption(product);
            product.validateOption(option);
            Option savedOption = optionJpaRepository.save(option);
            product.addOption(savedOption);
        }
    }

    public List<OptionDto> readAll(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        List<Option> options = product.getOptions();
        return options.stream().map(OptionDto::from).toList();
    }

    @Transactional
    public void update(Product product, List<OptionDto> optionRequestDtos) {
        optionJpaRepository.deleteAll(product.getOptions());
        product.removeOptions();
        create(product, optionRequestDtos);
    }

    public void deleteAllByProduct(Product product) {
        product.removeOptions();
        optionJpaRepository.deleteAllByProductId(product.getId());
    }
}
