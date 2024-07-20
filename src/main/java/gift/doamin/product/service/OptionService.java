package gift.doamin.product.service;

import gift.doamin.product.dto.OptionForm;
import gift.doamin.product.entity.Option;
import gift.doamin.product.entity.Product;
import gift.doamin.product.exception.ProductNotFoundException;
import gift.doamin.product.repository.JpaProductRepository;
import gift.doamin.product.repository.OptionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final JpaProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(JpaProductRepository productRepository,
        OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @productService.isOwner(authentication.getName(), #productId)")
    public void create(Long productId, OptionForm optionForm) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (optionRepository.existsByProductIdAndName(productId, optionForm.getName())) {
            throw new IllegalArgumentException("옵션 이름은 중복될 수 없습니다.");
        }

        optionRepository.save(new Option(product, optionForm.getName(), optionForm.getQuantity()));

    }
}
