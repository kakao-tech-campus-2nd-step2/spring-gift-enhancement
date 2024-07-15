package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.ProductRequestDto;
import gift.dto.response.ProductResponseDto;
import gift.exception.EntityNotFoundException;
import gift.exception.KakaoInNameException;
import gift.repository.category.CategoryRepository;
import gift.repository.product.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository
    ){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productDto){
        checkNameInKakao(productDto);

        Category category = categoryRepository.findById(productDto.categoryId()).orElseThrow(() -> new EntityNotFoundException("해당 카테고리는 존재하지 않습니다."));

        Product product = new Product.Builder()
                .name(productDto.name())
                .price(productDto.price())
                .imageUrl(productDto.imageUrl())
                .category(category)
                .build();

        product.addCategory(category);

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.from(savedProduct);
    }

    public ProductResponseDto findProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 상품입니다."));

        return ProductResponseDto.from(product);
    }

    public List<ProductResponseDto> findAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findProducts(Pageable pageable){
        return productRepository.findAll(pageable).stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto){
        checkNameInKakao(productRequestDto);

        Product findProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 상품입니다."));
        Category category = categoryRepository.findById(productRequestDto.categoryId()).orElseThrow(() -> new EntityNotFoundException("해당 카테고리가 존재하지 않습니다."));

        findProduct.update(productRequestDto, category);

        return ProductResponseDto.from(findProduct);
    }

    @Transactional
    public ProductResponseDto deleteProduct(Long id){
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 상품입니다."));

        productRepository.delete(findProduct);

        return ProductResponseDto.from(findProduct);
    }

    private void checkNameInKakao(ProductRequestDto productDto) {
        if(productDto.name().contains("카카오")){
            throw new KakaoInNameException();
        }
    }
}