package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.CategoryJpaDao;
import gift.repository.OptionJpaDao;
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
    private final OptionJpaDao optionJpaDao;

    public ProductService(ProductJpaDao productJpaDao, CategoryJpaDao categoryJpaDao,
        OptionJpaDao optionJpaDao) {
        this.productJpaDao = productJpaDao;
        this.categoryJpaDao = categoryJpaDao;
        this.optionJpaDao = optionJpaDao;
    }

    /**
     * 상품을 먼저 추가한 뒤, 옵션들을 추가.
     *
     * @param productRequest
     */
    public void addProduct(ProductRequest productRequest) {
        productJpaDao.findByName(productRequest.getName())
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_ALREADY_EXISTS_MSG);
            });
        Category category = categoryJpaDao.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        Product savedProduct = productJpaDao.save(new Product(productRequest, category));
        addOptions(productRequest, savedProduct);
    }

    @Transactional
    public void editProduct(ProductRequest productRequest) {
        Product targetProduct = productJpaDao.findById(productRequest.getId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        Category category = categoryJpaDao.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
        targetProduct.updateProduct(productRequest, category);
    }

    public void deleteProduct(Long id) {
        productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productJpaDao.deleteById(id);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productJpaDao.findAll(pageable).map(ProductResponse::new);
    }

    public ProductResponse getProduct(Long id) {
        Product product = productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        return new ProductResponse(product);
    }

    /**
     * 상품 추가 한 뒤, 함께 전달받은 옵션들을 등록하는 과정.
     *
     * @param productRequest 받은 요청
     * @param savedProduct   요청에 따라 저장된 상품 객체
     */
    private void addOptions(ProductRequest productRequest, Product savedProduct) {
        if (savedProduct == null) {
            throw new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG);
        }
        productRequest.getOptions().forEach(optionDto -> {
            optionJpaDao.findByNameAndProduct_Id(optionDto.getName(), savedProduct.getId())
                .ifPresent(v -> {
                    throw new IllegalArgumentException(ErrorMessage.OPTION_NAME_ALREADY_EXISTS_MSG);
                });
            optionJpaDao.save(new Option(optionDto, savedProduct));
        });
    }
}
