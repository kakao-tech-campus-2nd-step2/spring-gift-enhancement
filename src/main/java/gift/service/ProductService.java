package gift.service;

import gift.DTO.Product.ProductRequest;
import gift.DTO.Product.ProductResponse;
import gift.domain.Category;
import gift.domain.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /*
     * DB에 저장된 모든 Product 객체를 불러와 전달해주는 로직
     */
    public Page<ProductResponse> readAllProduct(int page, int size){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<Product> products = productRepository.findAll(pageable);

        return products.map(ProductResponse::new);
    }
    /*
     * DB에 저장된 Product를 ID를 기준으로 찾아 반환
     */
    public ProductResponse readOneProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(NoSuchFieldError::new);
        return new ProductResponse(product);
    }
    /*
     * 객체를 전달받아 DB에 저장
     */
    @Transactional
    public void createProduct(ProductRequest productRequest){
        Category category = categoryRepository.findByName(productRequest.getCategoryName());

        Product productEntity = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl(),
                category
        );
        productRepository.save(productEntity);
    }
    /*
     * DB에 있는 특정한 ID의 객체를 삭제해주는 로직
     */
    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    /*
     * 현재 DB에 존재하는 Product를 새로운 Product로 대체하는 로직
     */
    @Transactional
    public void updateProduct(ProductRequest productRequest, Long id){
        Product savedProduct = productRepository.findById(id).orElseThrow(NullPointerException::new);
        Category category = categoryRepository.findByName(productRequest.getCategoryName());

        savedProduct.updateEntity(
                productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(), category
        );;
    }
    /*
     * 새로운 ID가 기존 ID와 중복되었는지를 확인하는 로직
     */
    public boolean isDuplicate(Long id){
        return productRepository.existsById(id);
    }

}
