package gift.service;

import gift.domain.Product;
import gift.dto.OptionDTO;
import gift.exception.DuplicateOptionNameException;
import gift.exception.NoSuchProductException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public List<OptionDTO> getOptions(long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new);
        return optionRepository.findByProduct(product)
            .stream()
            .map(option -> option.toDTO())
            .toList();
    }

    public OptionDTO addOption(long productId, OptionDTO optionDTO) {
        Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new);
        checkNameDuplicate(product, optionDTO.name());
        return optionRepository.save(optionDTO.toEntity(product)).toDTO();
    }

    private void checkNameDuplicate(Product product, String name) {
        if (optionRepository.findByProduct(product)
            .stream()
            .anyMatch(option -> option.isSameName(name))) {
            throw new DuplicateOptionNameException();
        }
    }
}
