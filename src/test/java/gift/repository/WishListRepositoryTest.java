package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryRepository;
import gift.administrator.product.Product;
import gift.administrator.product.ProductRepository;
import gift.users.user.User;
import gift.users.user.UserRepository;
import gift.users.wishlist.WishList;
import gift.users.wishlist.WishListRepository;
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
        WishList expected = new WishList(user, product, 3);

        //When
        WishList actual = wishListRepository.save(wishList);

        //Then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
            .extracting(wish -> wish.getUser().getEmail(), wish -> wish.getProduct().getName(),
                wish -> wish.getProduct().getPrice(), wish -> wish.getProduct().getImageUrl(),
                wish -> wish.getProduct().getCategory(), WishList::getNum)
            .containsExactly(expected.getUser().getEmail(), expected.getProduct().getName(),
                expected.getProduct().getPrice(), expected.getProduct().getImageUrl(),
                expected.getProduct().getCategory(), expected.getNum());
    }

    @Test
    @DisplayName("이메일 위시리스트 전체 찾기")
    void findAllByEmail() {
        //Given
        Product product2 = new Product("라이언", 3000, "example.jpg", category);
        productRepository.save(product2);
        WishList wishList = new WishList(user, product, 3);
        WishList wishList1 = new WishList(user, product2, 5);
        wishListRepository.save(wishList);
        wishListRepository.save(wishList1);

        //When
        List<WishList> actual = wishListRepository.findAllByUserId(user.getId());

        //Then
        assertThat(actual).hasSize(2);
        assertThat(actual.getFirst().getId()).isNotNull();
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual)
            .containsExactly(wishList, wishList1);
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
