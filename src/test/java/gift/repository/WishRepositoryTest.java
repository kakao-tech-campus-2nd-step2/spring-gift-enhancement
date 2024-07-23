package gift.repository;

import gift.entity.Wish;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndFindById() {
        // given
        Category category = new Category("Sample Category", "Red", "sample-img-url", "Sample Description");
        categoryRepository.save(category);

        Product product = new Product("Sample Product", 100, "sample-img-url", category);
        productRepository.save(product);

        Member member = new Member("john.doe@example.com", "securepassword");
        memberRepository.save(member);

        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        // when
        Optional<Wish> foundWish = wishRepository.findById(wish.getId());

        // then
        assertThat(foundWish).isPresent();
        assertThat(foundWish.get().getId()).isEqualTo(wish.getId());
        assertThat(foundWish.get().getMember().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(foundWish.get().getProduct().getName()).isEqualTo("Sample Product");
    }
}