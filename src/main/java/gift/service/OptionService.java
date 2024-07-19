package gift.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.exception.OptionNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.option.OptionSpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.exception.ErrorCode.OPTION_NOT_FOUND;
import static gift.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
public class OptionService {
    @Autowired
    private ProductSpringDataJpaRepository productRepository;

    @Autowired
    private OptionSpringDataJpaRepository optionRepository;

    public List<Option> getOptionsByProductId(Long productId) {
        return optionRepository.findByProductId(productId);
    }

    public Option addOptionToProduct(Long productId, Option option) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        option.setProduct(product);
        return optionRepository.save(option);
    }

    public Option updateOption(Long optionId, Option optionDetails) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND));
        option.setName(optionDetails.getName());
        option.setQuantity(optionDetails.getQuantity());
        return optionRepository.save(option);
    }

    public void deleteOption(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException(OPTION_NOT_FOUND));
        optionRepository.delete(option);
    }

}
