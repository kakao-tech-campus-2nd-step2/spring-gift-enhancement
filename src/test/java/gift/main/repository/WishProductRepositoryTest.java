package gift.main.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gift.main.controller.AdminProductController;
import gift.main.entity.Category;
import gift.main.entity.Product;
import gift.main.entity.User;
import gift.main.entity.WishProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishProductRepositoryTest {
    @Autowired
    private  WishProductRepository wishProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void 유저삭제시_해당위시리스트_정상삭제() {
        //이부분 정상 작동 X
        User user = userRepository.save(new User("테스트용", "email", "123", "ADMIN"));
        User seller = userRepository.save(new User("셀러", "셀러", "123", "ADMIN"));

        Product product1 = productRepository.save(new Product("테스트용1", 1000, "url", seller, categoryRepository.getById(1l)));
        Product product2 = productRepository.save(new Product("테스트용2", 1000, "url", seller, categoryRepository.getById(1l)));

        wishProductRepository.save(new WishProduct(product1, user));
        wishProductRepository.save(new WishProduct(product2, user));


        Assertions.assertFalse(wishProductRepository.findAll().isEmpty());
        System.out.println("wishProductRepository.findAll() = " + wishProductRepository.findAll());
        Assertions.assertDoesNotThrow(() -> userRepository.delete(user));
        Assertions.assertFalse(() -> userRepository.existsByEmail("email"));
        System.out.println("wishProductRepository.findAll() = " + wishProductRepository.findAll());
        Assertions.assertTrue(wishProductRepository.findAll().isEmpty());


    }




    @Test
    public void 값삭제하기() {

    }


}