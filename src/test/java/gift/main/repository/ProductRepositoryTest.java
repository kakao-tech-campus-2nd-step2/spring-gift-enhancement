package gift.main.repository;

import gift.main.dto.CategoryRequest;
import gift.main.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final WishProductRepository wishProductRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository, CategoryRepository categoryRepository, WishProductRepository wishProductRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.wishProductRepository = wishProductRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void getWishProductOfProduct() {
        Category category = categoryRepository.save(new Category(new CategoryRequest(111, "Test Category")));
        User user = userRepository.save(new User("User", "123", "123", Role.ADMIN));
        Product product = productRepository.save(new Product("Product", 1200, "url", user, category));
        WishProduct wishProduct = wishProductRepository.save(new WishProduct(product, user));
        entityManager.flush();
        entityManager.clear();

        // When
        Product foundProduct = productRepository.findById(product.getId()).orElseThrow();

        // Then
        assertThat(foundProduct.getWishProducts().size()).isEqualTo(1);

    }


}