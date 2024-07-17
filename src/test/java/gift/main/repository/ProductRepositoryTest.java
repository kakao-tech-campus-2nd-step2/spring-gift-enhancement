package gift.main.repository;

import gift.main.dto.CategoryRequest;
import gift.main.dto.ProductRequest;
import gift.main.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WishProductRepository wishProductRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 위시상품있을때프로덕트삭제() {
        Category category = categoryRepository.save(new Category(new CategoryRequest(111, "Test Category")));
        User user = userRepository.save(new User("User", "123", "123", Role.ADMIN));
        Product product = productRepository.save(new Product("Product", 1200, "url", user, category));
        WishProduct wishProduct = wishProductRepository.save(new WishProduct(product, user));

        assertDoesNotThrow(() -> productRepository.deleteById(product.getId()));

        Optional<Product> deletedProduct = productRepository.findById(product.getId());
        assertTrue(deletedProduct.isEmpty());

    }


//    @Test
//    public void 모두조회() {
//        //given
//
//        productRepository.save(new Product("테스트1", 123, "123"));
//        productRepository.save(new Product("테스트2", 123, "123"));
//        productRepository.save(new Product("테스트3", 123, "123"));
//
//        //when
//        List<Product> productList = productRepository.findAll();
//
//        //then
//        assertEquals(3, productList.size());
//
//    }
//
//
//    @Test
//    public void 수정() {
//        //given
//        Product product = new Product("테스트용", 123, "123");
//        Product product1 = productRepository.save(product);
//
//        //when
//        String newName = "newName";
//        product1.updateValue(newName, 123, "123");
//        productRepository.save(product1);
//
//        //then
//        assertEquals(newName, productRepository.findById(product1.getId()).get().getName());
//
//    }
//
//    @Test
//    public void 삭제() {
//        //given
//        Product product = new Product("테스트용", 123, "123");
//        Product product1 = productRepository.save(product);
//
//        //when
//        productRepository.deleteById(product1.getId());
//
//        //then
//        assertTrue(productRepository.findById(product1.getId()).isEmpty());
//    }
}