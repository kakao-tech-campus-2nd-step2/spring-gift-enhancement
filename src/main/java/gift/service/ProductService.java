package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.ProductDto;
import gift.entity.Category;
import gift.entity.Product;
import gift.repository.CategoryJpaDao;
import gift.repository.ProductJpaDao;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaDao productJpaDao;
    private final CategoryJpaDao categoryJpaDao;

    public ProductService(ProductJpaDao productJpaDao, CategoryJpaDao categoryJpaDao) {
        this.productJpaDao = productJpaDao;
        this.categoryJpaDao = categoryJpaDao;
    }

    public void addProduct(ProductDto productDto) {
        productJpaDao.findByName(productDto.getName())
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_ALREADY_EXISTS_MSG);
            });
        Category category = categoryJpaDao.findById(productDto.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        productJpaDao.save(new Product(productDto, category));
    }

    @Transactional
    public void editProduct(ProductDto product) {
        Product targetProduct = productJpaDao.findById(product.getId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        Category category = categoryJpaDao.findById(product.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        targetProduct.updateProduct(product, category);
    }

    public void deleteProduct(Long id) {
        productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productJpaDao.deleteById(id);
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productJpaDao.findAll(pageable).map(ProductDto::new);
    }

    public ProductDto getProduct(Long id) {
        Product product = productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        return new ProductDto(product);
    }
}
