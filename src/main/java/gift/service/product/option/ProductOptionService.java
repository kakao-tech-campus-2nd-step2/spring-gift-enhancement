package gift.service.product.option;

import gift.domain.product.Product;
import gift.domain.product.option.ProductOption;
import gift.repository.product.ProductRepository;
import gift.repository.product.option.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductOptionService {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductOption addProductOption(Long productId, String name, Long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (productOptionRepository.existsByProductAndName(product, name)) {
            throw new IllegalArgumentException("Option name already exists for this product");
        }

        ProductOption option = new ProductOption(name, quantity, product);
        return productOptionRepository.save(option);
    }

    public List<ProductOption> getProductOptions(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }
}
