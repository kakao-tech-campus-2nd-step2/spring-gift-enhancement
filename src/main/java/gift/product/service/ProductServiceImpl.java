package gift.product.service;

import gift.core.PagedDto;
import gift.core.domain.product.*;
import gift.core.domain.product.exception.ProductAlreadyExistsException;
import gift.core.domain.product.exception.ProductNotFoundException;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository
    ) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Product get(Long id) {
        if (!productRepository.exists(id)) {
            throw new ProductNotFoundException();
        }
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public boolean exists(Long id) {
        return productRepository.exists(id);
    }

    @Override
    @Transactional
    public void createProductWithCategory(@Nonnull Product product) {
        if (productRepository.exists(product.id())) {
            throw new ProductAlreadyExistsException();
        }
        ProductCategory category = productCategoryRepository
                .findByName(product.category())
                .orElseGet(
                        () -> productCategoryRepository.save(ProductCategory.of(product.category()))
                );
        productRepository.save(product.withCategory(category));
    }

    @Override
    @Transactional
    public void updateProduct(@Nonnull Product product) {
        if (!productRepository.exists(product.id())) {
            throw new ProductNotFoundException();
        }
        ProductCategory category = productCategoryRepository
                .findByName(product.category())
                .orElseGet(
                        () -> productCategoryRepository.save(ProductCategory.of(product.category()))
                );
        productRepository.save(product.withCategory(category));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public PagedDto<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void remove(Long id) {
        if (!productRepository.exists(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.remove(id);
    }
}
