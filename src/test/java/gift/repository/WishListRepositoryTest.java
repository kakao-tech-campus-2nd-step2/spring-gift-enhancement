package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.category.Category;
import gift.category.CategoryRepository;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import gift.user.UserRepository;
import gift.wishlist.WishList;
import gift.wishlist.WishListRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;
    private Product product;
    private User user;

    @BeforeEach
    void beforeEach() {
        category = new Category("상품권", null, null, null);
        categoryRepository.save(category);
        product = new Product("이춘식", 1000, "image.jpg", category);
        productRepository.save(product);
        user = new User("admin@email.com", "1234");
        userRepository.save(user);
    }

    @Test
    @DisplayName("위시리스트 추가")
    void save() {
        //Given
        WishList wishList = new WishList(user, product, 3);

        //When
        WishList actual = wishListRepository.save(wishList);

        //Then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getUser().getEmail()).isEqualTo("admin@email.com");
        assertThat(actual.getUser().getPassword()).isEqualTo("1234");
        assertThat(actual.getProduct().getName()).isEqualTo("이춘식");
        assertThat(actual.getProduct().getPrice()).isEqualTo(1000);
        assertThat(actual.getProduct().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getNum()).isEqualTo(3);
    }

    @Test
    @DisplayName("이메일 위시리스트 전체 찾기")
    void findAllByEmail() {
        //Given
        Product product2 = new Product("라이언", 3000, "example.jpg", category);
        productRepository.save(product2);
        wishListRepository.save(new WishList(user, product, 3));
        wishListRepository.save(new WishList(user, product2, 5));

        //When
        List<WishList> actual = wishListRepository.findAllByUserId(user.getId());

        //Then
        assertThat(actual).hasSize(2);
        assertThat(actual.getFirst().getId()).isNotNull();
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual.getFirst().getProduct().getPrice()).isEqualTo(1000);
        assertThat(actual.getFirst().getProduct().getName()).isEqualTo("이춘식");
        assertThat(actual.getFirst().getProduct().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getFirst().getProduct().getCategory()).isEqualTo(category);
        assertThat(actual.getFirst().getNum()).isEqualTo(3);
        assertThat(actual.get(1).getProduct().getPrice()).isEqualTo(3000);
        assertThat(actual.get(1).getProduct().getName()).isEqualTo("라이언");
        assertThat(actual.get(1).getProduct().getImageUrl()).isEqualTo("example.jpg");
        assertThat(actual.get(1).getProduct().getCategory()).isEqualTo(category);
        assertThat(actual.get(1).getNum()).isEqualTo(5);
    }

    @Test
    @DisplayName("위시리스트 이메일과 상품 아이디로 삭제하기")
    void deleteByEmailAndProductId() {
        //Given
        WishList wishList = new WishList(user, product, 3);
        wishListRepository.save(wishList);

        //When
        wishListRepository.deleteByUserIdAndProductId(user.getId(), product.getId());
        Optional<WishList> actual = wishListRepository.findById(wishList.getId());

        //Then
        assertThat(actual).isNotPresent();
    }
}
