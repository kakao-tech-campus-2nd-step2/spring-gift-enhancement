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

    /**
     * 새 상품을 저장. 이미 존재하면 IllegalArgumentException
     *
     * @param productDto
     */
    public void addProduct(ProductDto productDto) {
        productJpaDao.findByName(productDto.getName())
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_ALREADY_EXISTS_MSG);
            });
        Category category = categoryJpaDao.findById(productDto.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        productJpaDao.save(new Product(productDto, category));
    }

    /**
     * 상품 정보 수정. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param product
     */
    @Transactional
    public void editProduct(ProductDto product) {
        Product targetProduct = productJpaDao.findById(product.getId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        Category category = categoryJpaDao.findById(product.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        targetProduct.updateProduct(product, category);
    }

    /**
     * 상품 삭제. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param id
     */
    public void deleteProduct(Long id) {
        productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productJpaDao.deleteById(id);
    }

    /**
     * 모든 상품 리스트 반환
     *
     * @return 상품 List
     */
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productJpaDao.findAll(pageable).map(ProductDto::new);
    }

    /**
     * id에 해당하는 상품 반환
     *
     * @param id
     * @return Product 객체
     */
    public ProductDto getProduct(Long id) {
        Product product = productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        return new ProductDto(product);
    }
}
