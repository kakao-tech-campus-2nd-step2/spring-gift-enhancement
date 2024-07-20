package gift.service;

import gift.dto.OptionDTO;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDTO> getOptionsByProductId(Long productId) {
        return optionRepository.findByProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OptionDTO createOption(Long productId, OptionDTO optionDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity(), product);
        Option savedOption = optionRepository.save(option);
        return convertToDTO(savedOption);
    }

    private OptionDTO convertToDTO(Option option) {
        return new OptionDTO(option.getId(), option.getName(), option.getQuantity());
    }
}