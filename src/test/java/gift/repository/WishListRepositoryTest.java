package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryRepository;
import gift.administrator.option.Option;
import gift.administrator.option.OptionRepository;
import gift.administrator.product.Product;
import gift.administrator.product.ProductRepository;
import gift.users.user.User;
import gift.users.user.UserRepository;
import gift.users.wishlist.WishList;
import gift.users.wishlist.WishListRepository;
import java.util.ArrayList;
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
    @Autowired
    private OptionRepository optionRepository;
    private Category category;
    private Product product;
    private User user;
    private Option option;
    private List<Option> options;

    @BeforeEach
    void beforeEach() {
        category = new Category("상품권", null, null, null);
        categoryRepository.save(category);
        option = new Option("XL", 3, product);
        options = new ArrayList<>(List.of(option));
        product = new Product("이춘식", 1000, "image.jpg", category, options);
        option.setProduct(product);
        productRepository.save(product);
        optionRepository.save(option);
        user = new User("admin@email.com", "1234");
        userRepository.save(user);
    }

    @Test
    @DisplayName("위시리스트 추가")
    void save() {
        //Given
        WishList wishList = new WishList(user, product, 3, option);
        WishList expected = new WishList(user, product, 3, option);

        //When
        WishList actual = wishListRepository.save(wishList);

        //Then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
            .extracting(wish -> wish.getUser().getEmail(), wish -> wish.getProduct().getName(),
                wish -> wish.getProduct().getPrice(), wish -> wish.getProduct().getImageUrl(),
                wish -> wish.getProduct().getCategory(), WishList::getNum,
                wish -> wish.getOption().getName(), wish -> wish.getOption().getProduct())
            .containsExactly(expected.getUser().getEmail(), expected.getProduct().getName(),
                expected.getProduct().getPrice(), expected.getProduct().getImageUrl(),
                expected.getProduct().getCategory(), expected.getNum(),
                expected.getOption().getName(), expected.getOption().getProduct());
    }

    @Test
    @DisplayName("이메일 위시리스트 전체 찾기")
    void findAllByEmail() {
        //Given
        Product product2 = new Product("라이언", 3000, "example.jpg", category, options);
        productRepository.save(product2);
        option.setProduct(product2);
        optionRepository.save(option);
        WishList wishList = new WishList(user, product, 3, option);
        WishList wishList1 = new WishList(user, product2, 5, option);
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
        WishList wishList = new WishList(user, product, 3, option);
        wishListRepository.save(wishList);

        //When
        wishListRepository.deleteByUserIdAndProductId(user.getId(), product.getId());
        Optional<WishList> actual = wishListRepository.findById(wishList.getId());

        //Then
        assertThat(actual).isNotPresent();
    }
}
