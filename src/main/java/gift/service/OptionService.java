package gift.service;

import gift.dto.OptionDTO;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> findOptionsByProductId(Long productId) {
        return optionRepository.findAllByProductId(productId);
    }

    @Transactional
    public void saveOption(OptionDTO optionDTO) {
        Product product = productRepository.findById(optionDTO.productId()).orElseThrow(() -> new IllegalArgumentException("유효한 옵션 ID가 아닙니다."));
        if (optionRepository.existsByProductIdAndName(optionDTO.productId(), optionDTO.name())) {
            throw new IllegalArgumentException("동일한 상품 내에 동일한 옵션 이름이 이미 존재합니다.");
        }
        Option option = new Option(null, optionDTO.name(), optionDTO.quantity(), product);
        optionRepository.save(option);
    }

    @Transactional
    public void updateOption(Long optionId, OptionDTO optionDTO) {
        Option existingOption = optionRepository.findById(optionId).orElseThrow(() -> new IllegalArgumentException("유효한 옵션 ID가 아닙니다."));
        if (!existingOption.getName().equals(optionDTO.name()) && optionRepository.existsByProductIdAndName(optionDTO.productId(), optionDTO.name())) {
            throw new IllegalArgumentException("동일한 상품 내에 동일한 옵션 이름이 이미 존재합니다.");
        }
        Option updatedOption = toEntity(optionDTO, optionId, existingOption.getProduct());
        optionRepository.save(updatedOption);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        List<Option> options = optionRepository.findAllByProductId(productId);
        if (options.size() <= 1) {
            throw new IllegalArgumentException("상품에는 하나 이상의 옵션이 있어야 합니다.");
        }
        optionRepository.deleteById(optionId);
    }

    public static OptionDTO toDTO(Option option) {
        return new OptionDTO(option.getName(), option.getQuantity(), option.getProduct().getId());
    }

    public static Option toEntity(OptionDTO optionDTO, Long id, Product product) {
        return new Option(id, optionDTO.name(), optionDTO.quantity(), product);
    }

}
