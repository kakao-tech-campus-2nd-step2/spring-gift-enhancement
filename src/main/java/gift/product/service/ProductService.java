package gift.product.service;

import gift.product.domain.Category;
import gift.product.domain.Product;
import gift.product.domain.ProductOption;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.CategoryRepository;
import gift.product.persistence.ProductOptionRepository;
import gift.product.persistence.ProductRepository;
import gift.product.service.command.ProductCommand;
import gift.product.service.command.ProductOptionCommand;
import gift.product.service.dto.ProductInfo;
import gift.product.service.dto.ProductPageInfo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository productOptionRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductOptionRepository productOptionRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Transactional
    public Long saveProduct(ProductCommand productRequest, List<ProductOptionCommand> productOptionRequests) {
        Category category = categoryRepository.findByName(productRequest.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        Product product = productRequest.toEntity(category);
        var newProduct = productRepository.save(product);// 이렇게 하지않으면, Stream에서 사용할 수 없다... 어떻게 해야하나

        List<ProductOption> productOptions = productOptionRequests.stream()
                .map(productOptionRequest -> productOptionRequest.toEntity(newProduct))
                .toList();

        productOptionRepository.saveAll(productOptions);
        return newProduct.getId();
    }

    @Transactional
    public void modifyProduct(final Long id, ProductCommand productRequest) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        var category = categoryRepository.findByName(productRequest.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        product.modify(productRequest.name(), productRequest.price(), productRequest.imgUrl(), category);
    }

    @Transactional(readOnly = true)
    public ProductInfo getProductDetails(final Long id) {
        Product foundProduct = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        return ProductInfo.from(foundProduct);
    }

    @Transactional(readOnly = true)
    public ProductPageInfo getProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllWithCategory(pageable);

        return ProductPageInfo.from(productPage);
    }

    @Transactional
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        productRepository.delete(product);
    }
}
