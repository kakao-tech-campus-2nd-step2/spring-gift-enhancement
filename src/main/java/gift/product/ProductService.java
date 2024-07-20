package gift.product;

import gift.category.CategoryErrorCode;
import gift.category.CategoryRepository;
import gift.category.model.Category;
import gift.common.exception.CategoryException;
import gift.common.exception.ProductException;
import gift.option.OptionRepository;
import gift.option.model.Option;
import gift.product.model.Product;
import gift.product.model.ProductRequest;
import gift.product.model.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return ProductResponse.from(productRepository.findById(id)
            .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND)));
    }

    @Transactional
    public Long insertProduct(ProductRequest.Create create)
        throws CategoryException, ProductException {
        Category category = categoryRepository.findById(create.categoryId())
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        Product product = new Product(create.name(), create.price(),
            create.imageUrl(), category);
        List<Option> options = create.optionRequests().stream()
            .map((it) -> new Option(it.name(), it.quantity(), product))
            .toList();
        productRepository.save(product);
        options.forEach(optionRepository::save);
        return product.getId();
    }

    @Transactional
    public void updateProductById(Long id, ProductRequest.Update update)
        throws CategoryException, ProductException {
        Category category = categoryRepository.findById(update.categoryId())
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));
        product.updateInfo(update.name(), update.price(),
            update.imageUrl(), category);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
