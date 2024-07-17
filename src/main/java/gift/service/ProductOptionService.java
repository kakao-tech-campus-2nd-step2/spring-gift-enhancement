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
        return productOptionRepository.findByProductId(productId); // 메서드 호출
    }

    public ProductOption findById(Long id) {
        Optional<ProductOption> productOption = productOptionRepository.findById(id);
        return productOption.orElse(null);
    }
}