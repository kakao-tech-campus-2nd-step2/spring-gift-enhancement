package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.DuplicateOptionNameException;
import gift.exception.InvalidProductDataException;
import gift.exception.ProductNotFoundException;
import gift.repository.category.CategorySpringDataJpaRepository;
import gift.repository.option.OptionSpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static gift.exception.ErrorCode.*;

@Service
@Transactional()
public class ProductService {
    private final ProductSpringDataJpaRepository productRepository;
    private final CategorySpringDataJpaRepository categoryRepository;
    private final OptionSpringDataJpaRepository optionRepository;

    @Autowired
    public ProductService(ProductSpringDataJpaRepository productRepository, CategorySpringDataJpaRepository categoryRepository, OptionSpringDataJpaRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public Product register(ProductRequest productRequest) {
        Category category = categoryRepository.findByName(productRequest.getCategoryName()).
                orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        List<Option> options = productRequest.getOptions().stream()
                .map(optionRequest -> new Option(optionRequest.getName(), optionRequest.getQuantity(), null))
                .collect(Collectors.toList());

        checkForDuplicateOptions(options);

        Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl(),
                category,
                options
        );

        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidProductDataException("상품 데이터가 유효하지 않습니다: " + e.getMessage(), e);
        }

    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findOne(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    public Product update(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findByName(productRequest.getCategoryName()).
                orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
        product.update(productRequest, category);
        productRepository.save(product);
        return product;

    }

    public Product delete(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return product;
    }

    private void checkForDuplicateOptions(List<Option> newOptions) {
        List<Option> allOptions = optionRepository.findAll();
        Set<String> existingOptionNames = allOptions.stream()
                .map(Option::getName)
                .collect(Collectors.toSet());

        Set<String> newOptionNames = new HashSet<>();
        for (Option option : newOptions) {
            if (!newOptionNames.add(option.getName())) {
                throw new DuplicateOptionNameException(DUPLICATE_OPTION_NAME);
            }
            if (existingOptionNames.contains(option.getName())) {
                throw new DuplicateOptionNameException(DUPLICATE_OPTION_NAME);
            }
        }
    }
}
