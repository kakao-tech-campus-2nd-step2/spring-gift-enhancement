package gift.domain.product;

import gift.domain.category.Category;
import gift.domain.category.JpaCategoryRepository;
import gift.domain.option.OptionService;
import gift.global.exception.BusinessException;
import gift.global.exception.ErrorCode;

import gift.global.exception.category.CategoryNotFoundException;
import gift.global.exception.product.ProductDuplicateException;
import gift.global.exception.product.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final JpaProductRepository productRepository;
    private final JpaCategoryRepository categoryRepository;
    private final OptionService optionService;
    private final Validator validator;

    @Autowired
    public ProductService(
        JpaProductRepository jpaProductRepository,
        JpaCategoryRepository jpaCategoryRepository,
        Validator validator,
        OptionService optionService
    ) {
        this.productRepository = jpaProductRepository;
        this.categoryRepository = jpaCategoryRepository;
        this.validator = validator;
        this.optionService = optionService;
    }

    /**
     * 상품 추가
     */
    public void createProduct(@Valid ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ProductDuplicateException(productDTO.getName());
        }
        if (categoryRepository.findById(productDTO.getCategoryId()).isEmpty()) {
            throw new CategoryNotFoundException(productDTO.getCategoryId());
        }

        Product product = new Product(
            productDTO.getName(),
            categoryRepository.findById(productDTO.getCategoryId()).get(),
            productDTO.getPrice(),
            productDTO.getImageUrl()
        );

        validateProduct(product);

        Product savedProduct = productRepository.save(product);
        // 옵션 저장
        optionService.addOption(savedProduct, productDTO.getOption());
    }

    /**
     * 전체 싱픔 목록 조회 - 페이징(매개변수별)
     */
    public Page<Product> getProductsByPageAndSort(int page, int size, Sort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageRequest);

        return products;
    }

    /**
     * 상품 수정
     */
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ProductDuplicateException(productDTO.getName());
        }

        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new CategoryNotFoundException(productDTO.getCategoryId());
        }

        product.update(productDTO.getName(), category.get(), productDTO.getPrice(),
            productDTO.getImageUrl());

        validateProduct(product);

        productRepository.save(product);
    }

    /**
     * 상품 삭제
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 해당 ID 리스트에 속한 상품들 삭제
     */
    public void deleteProductsByIds(List<Long> productIds) {
        if (productIds.isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "삭제할 상품을 선택하세요.");
        }

        productRepository.deleteAllByIdIn(productIds);
    }

    /**
     * 비즈니스 제약 사항 검사
     */
    public void validateProduct(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new BusinessException(ErrorCode.BAD_REQUEST, message);
        }
    }

}


