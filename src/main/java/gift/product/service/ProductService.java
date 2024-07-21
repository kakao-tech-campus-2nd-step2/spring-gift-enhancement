package gift.product.service;

import gift.category.entity.Category;
import gift.global.dto.CategoryInfoDto;
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
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // repository를 호출해서 productDTO를 DB에 넣는 함수
    @Transactional
    public void insertProduct(ProductRequestDto productRequestDto,
        CategoryInfoDto categoryInfoDto) {
        Product product = new Product(productRequestDto.name(), productRequestDto.price(),
            productRequestDto.imageUrl(), new Category(categoryInfoDto.categoryId(),
            categoryInfoDto.name(), categoryInfoDto.imageUrl()));
        productRepository.save(product);
    }

    // repository를 호출해서 DB에 담긴 로우를 반환하는 함수
    @Transactional(readOnly = true)
    public ProductResponseDto selectProduct(long productId) {
        // 불러오면서 확인
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = getActualProduct(optionalProduct);

        return new ProductResponseDto(product.getProductId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategory().getCategoryId(),
            product.getCategory().getName(), product.getCategory().getImageUrl());
    }

    // 전체 제품 정보를 반환하는 함수
    @Transactional(readOnly = true)
    public List<ProductResponseDto> selectProducts(PageInfoDto pageInfoDto) {
        Pageable pageable = pageInfoDto.pageRequest();

        Page<Product> products = productRepository.findAll(pageable);
        return products.stream().map(product -> new ProductResponseDto(product.getProductId(),
                product.getName(), product.getPrice(), product.getImageUrl(),
                product.getCategory().getCategoryId(),
                product.getCategory().getName(), product.getCategory().getImageUrl()))
            .collect(Collectors.toList());
    }

    // repository를 호출해서 특정 로우를 파라메터로 전달된 DTO로 교체하는 함수
    @Transactional
    public void updateProduct(long productId, ProductRequestDto productRequestDto,
        CategoryInfoDto categoryInfoDto) {
        // 불러오면서 확인
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = getActualProduct(optionalProduct);

        product.updateProduct(productRequestDto.name(), productRequestDto.price(),
            productRequestDto.imageUrl(), new Category(categoryInfoDto.categoryId(),
                categoryInfoDto.name(), categoryInfoDto.imageUrl()));
    }

    // repository를 호출해서 특정 로우를 제거하는 함수
    @Transactional
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    // 제품이 존재하는지 검증 (쿼리 두번 날리지 않도록)
    private Product getActualProduct(Optional<Product> optionalProduct) {
        if (optionalProduct.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 제품입니다.");
        }

        return optionalProduct.get();
    }
}
