package gift.domain.service;

import gift.domain.dto.request.ProductAddRequest;
import gift.domain.dto.request.ProductUpdateRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Product;
import gift.domain.exception.conflict.ProductAlreadyExistsException;
import gift.domain.exception.notFound.ProductNotFoundException;
import gift.domain.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        //존재하지 않는 상품이면 예외 발생
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductResponse::of)
            .toList();
    }

    @Transactional
    public ProductResponse addProduct(ProductAddRequest requestDto) {
        //이미 존재하는 상품 등록 시도시 예외 발생
        productRepository.findByContents(requestDto).ifPresent((p) -> {
            throw new ProductAlreadyExistsException();});

        return ProductResponse.of(productRepository.save(requestDto.toEntity(categoryService)));
    }

    @Transactional
    public ProductResponse updateProductById(Long id, ProductUpdateRequest updateRequestDto) {
        //존재하지 않는 상품 업데이트 시도시 예외 발생
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        //TODO: 상품 업데이트시 기존 상품과 겹칠 경우 막는 로직 추가필요
        product.set(updateRequestDto, categoryService);
        //상품 업데이트
        return ProductResponse.of(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id)
            //존재하지 않는 상품 삭제 시도시 예외 발생
            .orElseThrow(ProductNotFoundException::new));
    }
}
