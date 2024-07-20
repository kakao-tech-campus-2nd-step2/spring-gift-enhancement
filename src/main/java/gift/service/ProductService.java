package gift.service;

import gift.dto.ProductResponseDto;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.ProductRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
@Service
@Transactional
public class ProductService {

    private final ProductRepositoryInterface productRepositoryInterface;

    public ProductService(ProductRepositoryInterface productRepositoryInterface) {
        this.productRepositoryInterface = productRepositoryInterface;
    }

    public ProductResponseDto createProductDto(String name, Integer price, String url, Category category, List<Option> options) {
        Product newProduct = productRepositoryInterface.save(new Product(name, price, url, category, options));
        return new ProductResponseDto(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getUrl(), newProduct.getCategory(), newProduct.getOptions());
    }

    public List<Product> getAll() {
        return productRepositoryInterface.findAll();
    }

    public ProductResponseDto getAllAndMakeProductResponseDto() {
        return new ProductResponseDto(getAll());
    }

    public ProductResponseDto getProductResponseDtoById(Long id) {
        Product newProduct = productRepositoryInterface.findById(id).get();
        return new ProductResponseDto(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getUrl(), newProduct.getCategory(), newProduct.getOptions());
    }


    public void update(Long id, String name, Integer price, String url, Category category,List<Option> options) {
        Product actualProduct = productRepositoryInterface.findById(id).orElseThrow(() -> new RuntimeException("상품을 찾지 못했습니다."));
        actualProduct.update(name, price, url, category,options);
    }

    public void delete(Long id) {
        Product product = productRepositoryInterface.findById(id).get();
        productRepositoryInterface.delete(product);
    }

    public Product findProductByName(String name) {
        return productRepositoryInterface.findByName(name);
    }

    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        Page<Product> newProduct = productRepositoryInterface.findAll(pageable);
        return newProduct.map(ProductResponseDto::fromEntity);
    }

    public Product findById(Long productId) {
        return productRepositoryInterface.findById(productId).get();
    }
}
