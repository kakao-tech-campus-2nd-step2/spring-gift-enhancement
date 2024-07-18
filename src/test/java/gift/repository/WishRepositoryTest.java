package gift.repository;

import gift.model.category.Category;
import gift.model.gift.Gift;
import gift.model.option.Option;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.repository.gift.GiftRepository;
import gift.repository.user.UserRepository;
import gift.repository.wish.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiftRepository giftRepository;


    private User user;
    private Gift gift;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password");

        userRepository.save(user);

        Category category = new Category(10L, "test", "test", "test", "test");
        Option option1 = new Option("testOption", 1);
        List<Option> option = Arrays.asList(option1);

        gift = new Gift("Test Gift", 100, "test.jpg", category, option);
        giftRepository.save(gift);

        Wish wish = new Wish(user, gift, 1);
        wishRepository.save(wish);
    }

    @Test
    void testFindByUser() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> wishes = wishRepository.findByUser(user, pageable);

        assertThat(wishes).isNotNull();
        assertThat(wishes.getContent()).hasSize(1);
        assertThat(wishes.getContent().get(0).getGift().getName()).isEqualTo("Test Gift");
    }

    @Test
    void testFindByUserAndGift() {
        List<Wish> wishes = wishRepository.findByUserAndGift(user, gift);

        assertThat(wishes).isNotNull();
        assertThat(wishes).hasSize(1);
        assertThat(wishes.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    void testDeleteByUserAndGift() {
        wishRepository.deleteByUserAndGift(user, gift);

        List<Wish> wishes = wishRepository.findByUserAndGift(user, gift);

        assertTrue(wishes.isEmpty());
    }
}
