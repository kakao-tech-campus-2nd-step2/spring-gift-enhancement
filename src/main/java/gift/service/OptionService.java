package gift.service;

import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        return product.getOptions();
    }

    public Option addOption(Long productId, Option option) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        product.getOptions().add(option);
        productRepository.save(product);
        return option;
    }
}
