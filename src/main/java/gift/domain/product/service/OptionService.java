package gift.domain.product.service;

import gift.domain.product.dto.OptionRequest;
import gift.domain.product.dto.OptionResponse;
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

    public void create(Product product, List<OptionRequest> optionRequestDtos) {
        for (OptionRequest optionRequest : optionRequestDtos) {
            Option option = optionRequest.toOption(product);
            product.validateOption(option);
            Option savedOption = optionJpaRepository.save(option);
            product.addOption(savedOption);
        }
    }

    public List<OptionResponse> readAll(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        List<Option> options = product.getOptions();
        return options.stream().map(OptionResponse::from).toList();
    }

    @Transactional
    public void update(Product product, List<OptionRequest> optionRequestDtos) {
        optionJpaRepository.deleteAll(product.getOptions());
        product.removeOptions();
        create(product, optionRequestDtos);
    }

    public void deleteAllByProduct(Product product) {
        product.removeOptions();
        optionJpaRepository.deleteAllByProductId(product.getId());
    }
}
