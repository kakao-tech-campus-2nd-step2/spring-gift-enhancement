package gift.repository;

import gift.common.enums.Role;
import gift.config.JpaConfig;
import gift.model.Category;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Member id로 Wish Select할 때 fetch join 테스트[성공]")
    void findAllByMemberIdFetchJoin() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String[] names = {"test1", "test2"};
        int[] prices = {10, 20};
        String[] imageUrls = {"test1", "test2"};
        Category category = categoryRepository.save(new Category("가전", "#123", "url", ""));
        Product[] products = {
                productRepository.save(new Product(names[0], prices[0], imageUrls[0], category)),
                productRepository.save(new Product(names[1], prices[1], imageUrls[1], category))};
        int[] productCounts = {1, 2};
        Wish[] wishes = {
                wishRepository.save(new Wish(member, productCounts[0], products[0])),
                wishRepository.save(new Wish(member, productCounts[1], products[1]))};

        // when
        Page<Wish> actuals = wishRepository.findAllByMemberIdFetchJoin(member.getId(),
                PageRequest.of(0, 10, Sort.by("createdAt").descending()));

        // then
        assertThat(actuals).hasSize(wishes.length);
        for(int i=0; i < actuals.getContent().size(); i++) {
            Wish result = actuals.getContent().get(i);
            assertThat(result.getId()).isNotNull();
            assertThat(result.getId()).isNotNull();
            assertThat(result.getMember().getEmail()).isEqualTo(email);
            assertThat(result.getMember().getPassword()).isEqualTo(password);
            assertThat(result.getMember().getRole()).isEqualTo(role);

            assertThat(result.getProduct().getCategory().getName()).isEqualTo(category.getName());

            assertThat(result.getProductCount()).isEqualTo(productCounts[i]);
        }
        assertThat(actuals.getContent().get(0).getCreatedAt()).isAfterOrEqualTo(actuals.getContent().get(1).getCreatedAt());
    }

    @Test
    @DisplayName("Wish id로 Select할 때 fetch join 테스트[성공]")
    void findByIdFetchJoin() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        int price = 1000;
        String imageUrl = "imageUrl";
        Category category = categoryRepository.save(new Category("가전", "#123", "url", ""));
        Product product = productRepository.save(new Product(name, price, imageUrl, category));
        int productCount = 10;
        Wish wish = new Wish(
                new Member(member.getId(), null, null, null, null, null),
                productCount,
                new Product(product.getId(), null, 0, null, null, null, null));

        // when
        Long wishId = wishRepository.save(wish).getId();
        entityManager.clear();  // 영속성 컨텍스트 초기화
        Wish actual = wishRepository.findByIdFetchJoin(wishId).orElseThrow();

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getMember().getEmail()).isEqualTo(email);
        assertThat(actual.getMember().getPassword()).isEqualTo(password);
        assertThat(actual.getMember().getRole()).isEqualTo(role);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getProduct().getName()).isEqualTo(name);
        assertThat(actual.getProduct().getPrice()).isEqualTo(price);
        assertThat(actual.getProduct().getImageUrl()).isEqualTo(imageUrl);
        assertThat(actual.getProduct().getCreatedAt()).isNotNull();
        assertThat(actual.getProduct().getUpdatedAt()).isNotNull();

        assertThat(actual.getProductCount()).isEqualTo(productCount);
    }

    @Test
    @DisplayName("Product와 Member id로 삭제 테스트[성공]")
    void deleteByProductIdAndMemberId() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        int price = 1000;
        String imageUrl = "imageUrl";
        Category category = categoryRepository.save(new Category("가전", "#123", "url", ""));
        Product product = productRepository.save(new Product(name, price, imageUrl, category));
        int productCount = 10;
        wishRepository.save(new Wish(member, productCount, product));

        // when
        wishRepository.deleteByProductIdAndMemberId(product.getId(), member.getId());
        List<Wish> actuals = wishRepository.findAll();

        // then
        assertThat(actuals.size()).isZero();
    }

    @Test
    void existsByProductIdAndMemberId() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        Category category = categoryRepository.save(new Category("가전", "#123", "url", ""));
        int price = 1000;
        String imageUrl = "imageUrl";
        Product product = productRepository.save(new Product(name, price, imageUrl, category));
        int productCount = 10;
        wishRepository.save(new Wish(member, productCount, product));

        // when
        boolean actual = wishRepository.existsByProductIdAndMemberId(product.getId(), member.getId());

        // then
        assertThat(actual).isTrue();
    }
}