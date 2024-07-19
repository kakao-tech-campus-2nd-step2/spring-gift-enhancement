package gift.product.service;

import gift.global.dto.PageInfoDto;
import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    // repository를 호출해서 productDTO를 DB에 넣는 함수
    public void insertProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.name(), productRequestDto.price(),
            productRequestDto.imageUrl());
        productRepository.save(product);
    }

    // repository를 호출해서 DB에 담긴 로우를 반환하는 함수
    public ProductResponseDto selectProduct(long productId) {
        // id가 존재하면 불러오기
        Optional<Product> optionalProduct = productRepository.findById(productId);
        verifyExistence(optionalProduct);
        Product product = optionalProduct.get();

        return new ProductResponseDto(product.getProductId(), product.getName(), product.getPrice(),
            product.getImage());
    }

    // 전체 제품 정보를 반환하는 함수
    public List<ProductResponseDto> selectProducts(PageInfoDto pageInfoDto) {
        Pageable pageable = pageInfoDto.pageRequest();

        Page<Product> products = productRepository.findAll(pageable);
        return products.stream().map(product -> new ProductResponseDto(product.getProductId(),
                product.getName(), product.getPrice(), product.getImage()))
            .collect(Collectors.toList());
    }

    // repository를 호출해서 특정 로우를 파라메터로 전달된 DTO로 교체하는 함수
    @Transactional
    public void updateProduct(long productId, ProductRequestDto productRequestDto) {
        // id가 존재하면 불러오기
        Optional<Product> optionalProduct = productRepository.findById(productId);
        verifyExistence(optionalProduct);
        Product product = optionalProduct.get();

        product.updateProduct(productRequestDto.name(), productRequestDto.price(),
            productRequestDto.imageUrl());
    }

    // repository를 호출해서 특정 로우를 제거하는 함수
    @Transactional
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    // 제품이 존재하는지 검증 (쿼리 두번 날리지 않도록)
    private void verifyExistence(Optional<Product> optionalProduct) {
        if (optionalProduct.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 제품입니다.");
        }
    }
}
