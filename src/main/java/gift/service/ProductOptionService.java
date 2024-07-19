package gift.service;

import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOptionService {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    public List<ProductOption> getOptionsByProductId(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }

    public ProductOption findProductOptionById(Long id) {
        Optional<ProductOption> productOption = productOptionRepository.findById(id);
        return productOption.orElse(null);
    }

    public void saveProductOption(ProductOption option) {
        productOptionRepository.save(option);
    }

    public void saveProductOptions(List<ProductOption> options) {
        for (ProductOption option : options) {
            if (!existsByProductIdAndName(option.getProduct().getId(), option.getName())) {
                productOptionRepository.save(option);
            }
        }
    }

    public boolean existsByProductIdAndName(Long productId, String name) {
        return productOptionRepository.existsByProductIdAndName(productId, name);
    }

    public void deleteProductOptionsByProductId(Long productId) {
        List<ProductOption> options = productOptionRepository.findByProductId(productId);
        productOptionRepository.deleteAll(options);
    }

    public void subtractProductOptionQuantity(Long optionId, int quantityToSubtract) {
        ProductOption option = findProductOptionById(optionId);
        if (option == null) {
            throw new IllegalArgumentException("Option not found");
        }
        option.subtractQuantity(quantityToSubtract);
        productOptionRepository.save(option);
    }
}
