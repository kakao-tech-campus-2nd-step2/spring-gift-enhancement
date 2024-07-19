package gift.option.service;

import gift.option.dto.OptionDto;
import gift.option.model.Option;
import gift.option.repository.OptionRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OptionDto> getOptionsByProductId(Long productId) {
        return optionRepository.findByProductId(productId).stream()
                .map(option -> new OptionDto(option.getOptionId(), option.getName(), option.getQuantity()))
                .collect(Collectors.toList());
    }

    public OptionDto addOptionToProduct(Long productId, OptionDto optionDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Option option = new Option(product, optionDto.getName(), optionDto.getQuantity());
        Option savedOption = optionRepository.save(option);

        return new OptionDto(savedOption.getOptionId(), savedOption.getName(), savedOption.getQuantity());
    }
}
