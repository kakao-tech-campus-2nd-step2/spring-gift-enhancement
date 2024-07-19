package gift.service;

import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final WishlistRepository wishlistRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, WishlistRepository wishlistRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
        Product updateProduct = new Product(
                id,
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                category
        );
        return productRepository.save(updateProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        wishlistRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
    }

    public void updateProductCategoryToNone(Category category) {
        Category noneCategory = categoryRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException("없음 카테고리를 찾을 수 없습니다."));
        List<Product> products = productRepository.findByCategory(category);
        for (Product product : products) {
            Product updateProduct = new Product(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl(),
                    noneCategory
            );
            productRepository.save(updateProduct);
        }
    }
}
