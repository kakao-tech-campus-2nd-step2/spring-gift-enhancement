package gift.service;

import gift.domain.Product;
import gift.domain.Option;
import gift.dto.OptionDTO;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDTO> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream()
                .map(option -> new OptionDTO(option.getId(), option.getName(), option.getQuantity()))
                .collect(Collectors.toList());
    }

    public OptionDTO addOption(Long productId, OptionDTO optionDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        if (optionRepository.existsByProductIdAndName(productId, optionDTO.getName())) {
            throw new IllegalArgumentException("Option name already exists for this product");
        }

        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity(), product);
        Option savedOption = optionRepository.save(option);

        return new OptionDTO(savedOption.getId(), savedOption.getName(), savedOption.getQuantity());
    }

    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
