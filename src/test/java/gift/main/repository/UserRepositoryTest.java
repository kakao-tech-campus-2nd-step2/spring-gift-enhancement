package gift.main.repository;

import gift.main.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryTest(CategoryRepository categoryRepository,
                              UserRepository userRepository,
                              WishProductRepository wishProductRepository,
                              ProductRepository productRepository,
                              EntityManager entityManager,
                              EntityManager entityManager1) {

        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;

        this.entityManager = entityManager1;
    }

    @Test
    @DisplayName("User에서 자식 WhishProduct가 존재할때, 정상삭제되는 지 조회")
    void deleteUserTest() {
        //given
        User user = new User("user", "user@", "1234", "ADMIN");
        User seller = new User("seller", "seller@", "1234", "ADMIN");
        user = userRepository.save(user);
        seller = userRepository.save(seller);

        Category category = categoryRepository.findByName("패션").get();
        Product product = new Product("testProduct", 12000, "Url", seller, category);
        product = productRepository.save(product);

        WishProduct wishProduct = new WishProduct(product, user);
        wishProductRepository.save(wishProduct);
        entityManager.flush();
        entityManager.clear();


        //when
        User searchUser = userRepository.findByEmail("user@").get();
        userRepository.delete(searchUser);
        entityManager.flush();
        entityManager.clear();


        //then
        assertThat(wishProductRepository.existsAllByUserId(user.getId())).isEqualTo(false);
    }

    @Test
    public void getUserRoleTest() {
        //given
        User user = new User("testuser", "email", "1234", "admin");

        //when
        User saveUser = userRepository.save(user);

        //then
        assertThat(saveUser.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    public void findAllTest() {
        //given
        userRepository.save(new User("name", "123@123", "123", "USER"));
        userRepository.save(new User("name", "1234@123", "123", "USER"));

        //when
        List<User> userList = userRepository.findAll();

        //then
        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void saveDuplicateEmailTest() {
        //given
        final String EMAIL = "이메일";
        User user = new User("name", EMAIL, "123", "USER");
        userRepository.save(user);

        User duplicateUser = new User("name1", EMAIL, "123", "USER");

        //when
        //then
        assertThatThrownBy(() -> userRepository.save(duplicateUser)).isInstanceOf(DataIntegrityViolationException.class);

    }
}