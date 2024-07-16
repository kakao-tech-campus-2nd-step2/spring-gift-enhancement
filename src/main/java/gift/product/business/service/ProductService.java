package gift.product.business.service;

import gift.product.business.dto.ProductPagingDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.CategoryRepository;
import gift.product.persistence.repository.ProductRepository;
import gift.product.business.dto.ProductDto;
import gift.product.business.dto.ProductRegisterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public ProductDto getProduct(Long id) {
        Product product = productRepository.getProductById(id);
        return ProductDto.from(product);
    }

    public Long createProduct(ProductRegisterDto productRegisterDto) {
        var category = categoryRepository.getReferencedCategory(productRegisterDto.categoryId());
        Product product = productRegisterDto.toProduct(category);
        return productRepository.saveProduct(product);
    }

    public Long updateProduct(ProductRegisterDto productRegisterDto, Long id) {
        var product = productRepository.getProductById(id);
        var category = categoryRepository.getReferencedCategory(productRegisterDto.categoryId());
        product.update(productRegisterDto.name(), productRegisterDto.description(),
                productRegisterDto.price(), productRegisterDto.url());
        product.setCategory(category);
        return productRepository.saveProduct(product);
    }

    public Long deleteProduct(Long id) {
        return productRepository.deleteProductById(id);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return ProductDto.of(products);
    }

    public void deleteProducts(List<Long> productIds) {
        productRepository.deleteProductByIdList(productIds);
    }

    public ProductPagingDto getProductsByPage(Pageable pageable) {
        Page<Product> products = productRepository.getProductsByPage(pageable);
        return ProductPagingDto.from(products);
    }
}
