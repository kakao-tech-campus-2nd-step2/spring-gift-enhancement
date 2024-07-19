package gift.domain.product.service;

import gift.domain.product.dto.OptionDto;
import gift.domain.product.entity.Option;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.OptionJpaRepository;
import gift.domain.product.repository.ProductJpaRepository;
import gift.exception.InvalidOptionInfoException;
import gift.exception.InvalidProductInfoException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public OptionService(OptionJpaRepository optionJpaRepository, ProductJpaRepository productJpaRepository) {
        this.optionJpaRepository = optionJpaRepository;
        this.productJpaRepository = productJpaRepository;

    }

    public OptionDto create(long productId, OptionDto optionRequestDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        Option option = optionRequestDto.toOption(product);

        product.validateOption(option);
        Option savedOption = optionJpaRepository.save(option);
        product.addOption(savedOption);
        return OptionDto.from(savedOption);
    }

    public List<OptionDto> readAll(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        List<Option> options = product.getOptions();
        return options.stream().map(OptionDto::from).toList();
    }

    public OptionDto update(long productId, OptionDto optionRequestDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
        Option oldOption = product.getOption(optionRequestDto.id())
            .orElseThrow(() -> new InvalidOptionInfoException("error.invalid.option.id"));
        Option newOption = optionRequestDto.toOption(product);

        product.validateOption(newOption);
        Option savedOption = optionJpaRepository.save(newOption);
        product.replaceOption(oldOption, savedOption);
        return OptionDto.from(savedOption);
    }
}
