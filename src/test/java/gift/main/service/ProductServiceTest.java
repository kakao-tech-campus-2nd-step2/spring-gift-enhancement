package gift.main.service;

import gift.main.entity.Category;
import gift.main.entity.Product;
import gift.main.entity.User;
import gift.main.entity.WishProduct;
import gift.main.repository.CategoryRepository;
import gift.main.repository.ProductRepository;
import gift.main.repository.UserRepository;
import gift.main.repository.WishProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class ProductServiceTest {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    ProductServiceTest(ProductService productService, UserRepository userRepository, CategoryRepository categoryRepository, WishProductRepository wishProductRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
    }



    @Test
    void deleteProductTest() {
        //given
        User user = new User("user", "user@", "1234", "ADMIN");
        User seller = new User("seller", "seller@", "1234", "ADMIN");
        userRepository.save(user);
        userRepository.save(seller);


        Category category = categoryRepository.findByName("패션").get();
        User seller1 = userRepository.findByEmail("seller@").get();
        Product product = new Product("testProduct1", 12000, "Url", seller1, category);
        Product saveProduct = productRepository.save(product);
        Long productId = saveProduct.getId();
        entityManager.flush();
        entityManager.clear();

        User user1 = userRepository.findByEmail("user@").get();
        Product product1 = productRepository.findById(productId).get();
        WishProduct wishProduct = new WishProduct(product1, user1);
        WishProduct saveWishProduct = wishProductRepository.save(wishProduct);
        Long wishProductId = saveWishProduct.getId();
        entityManager.flush();
        entityManager.clear();


        //when
        productService.deleteProduct(productId);
        entityManager.flush();
        entityManager.clear();

        //then
        wishProduct = wishProductRepository.findById(wishProductId).get();
        assertThat(wishProduct.getProduct()).isNull();
    }
}