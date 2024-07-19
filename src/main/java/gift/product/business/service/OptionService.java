package gift.product.business.service;

import gift.product.business.dto.OptionDto;
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
        if(isOptionNamesDuplicate(optionRegisterDtos)) {
            throw new IllegalArgumentException("옵션 이름이 중복되었습니다.");
        }
        var product = productRepository.getReferencedProduct(productId);
        var options = optionRegisterDtos.stream()
            .map(optionRegisterDto -> optionRegisterDto.toOption(product))
            .toList();
        return optionRepository.saveAll(options);
    }

    @Transactional(readOnly = true)
    public List<OptionDto> getOptionsByProduct(Long productId) {
        var options = optionRepository.getOptionsByProductId(productId);
        return OptionDto.of(options);
    }

    private boolean isOptionNamesDuplicate(List<OptionRegisterDto> optionRegisterDtos) {
        return optionRegisterDtos.stream()
            .map(OptionRegisterDto::name)
            .distinct()
            .count() != optionRegisterDtos.size();
    }
}
