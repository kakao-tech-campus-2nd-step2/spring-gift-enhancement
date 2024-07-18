package gift.service;

import gift.exception.category.NotFoundCategoryException;
import gift.exception.option.NotFoundOptionsException;
import gift.exception.product.NotFoundProductException;
import gift.model.Options;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.OptionsRepository;
import gift.repository.ProductRepository;
import gift.response.OptionResponse;
import gift.response.ProductAllOptionsResponse;
import gift.response.ProductOptionResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPagedAllProducts(Pageable pageable) {
        return productRepository.findPageBy(pageable);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public Product addProduct(String name, Integer price, String imageUrl, String categoryName) {
        return categoryRepository.findByName(categoryName)
            .map(
                category -> productRepository.save(new Product(name, price, imageUrl, category))
            ).orElseThrow(NotFoundCategoryException::new);
    }

    @Transactional
    public void updateProduct(Long id, String name, Integer price, String imageUrl,
        String categoryName) {
        productRepository.findById(id)
            .map(product -> {
                if (product.getCategory() == null) {
                    throw new NotFoundCategoryException();
                }
                product.updateProduct(name, price, imageUrl, categoryName);
                return product;
            })
            .orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
            .ifPresentOrElse(productRepository::delete
                , () -> {
                    throw new NotFoundProductException();
                }
            );
    }
}
