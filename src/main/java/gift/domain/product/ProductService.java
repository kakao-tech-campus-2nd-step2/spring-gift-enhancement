package gift.domain.product;

import gift.domain.Category.Category;
import gift.domain.Category.JpaCategoryRepository;
import gift.global.exception.BusinessException;
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

@Service
public class ProductService {

    private final JpaProductRepository productRepository;
    private final JpaCategoryRepository categoryRepository;
    private final Validator validator;

    @Autowired
    public ProductService(
        JpaProductRepository jpaProductRepository,
        JpaCategoryRepository jpaCategoryRepository,
        Validator validator
    ) {
        this.productRepository = jpaProductRepository;
        this.categoryRepository = jpaCategoryRepository;
        this.validator = validator;
    }

    /**
     * 상품 추가
     */
    public void createProduct(@Valid ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 이름의 상품이 이미 존재합니다.");
        }
        if (categoryRepository.findById(productDTO.getCategoryId()).isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재하지 않습니다.");
        }

        Product product = new Product(
            productDTO.getName(),
            categoryRepository.findById(productDTO.getCategoryId()).get(),
            productDTO.getPrice(),
            productDTO.getImageUrl()
        );

        validateProduct(product);

        productRepository.save(product);
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
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "수정할 상품이 존재하지 않습니다."));

        if (productRepository.existsByName(productDTO.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 이름의 상품이 이미 존재합니다.");
        }

        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재하지 않습니다.");
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
            throw new BusinessException(HttpStatus.BAD_REQUEST, "삭제할 상품을 선택하세요.");
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
            throw new BusinessException(HttpStatus.BAD_REQUEST, message);
        }
    }

}


