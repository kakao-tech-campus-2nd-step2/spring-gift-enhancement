package gift.service;

import gift.common.enums.Role;
import gift.config.JpaConfig;
import gift.controller.dto.request.CreateWishRequest;
import gift.controller.dto.request.UpdateWishRequest;
import gift.model.Category;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.CategoryRepository;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
class WishServiceTest {
    @Autowired
    private WishService wishService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("Wish 수정 테스트[성공]")
    void update() {
        // given
        Category category = categoryRepository.save(new Category("cname", "color", "imageUrl", "description"));
        Member member = memberRepository.save(new Member("mname", "mage", Role.USER));
        Product product = productRepository.save(new Product("pname", 1_000, "pimage", category));
        Wish wish = wishRepository.save(new Wish(member, 1, product));
        int productCount = 10;
        UpdateWishRequest request = new UpdateWishRequest(product.getId(), productCount);

        // when
        wishService.update(wish.getId(), request, member.getId());
        Wish actual = wishRepository.findById(wish.getId()).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getProduct().getId()).isEqualTo(product.getId());
        assertThat(actual.getMember().getId()).isEqualTo(member.getId());
        assertThat(actual.getProductCount()).isEqualTo(productCount);
    }

    @Test
    @DisplayName("Wish 저장 테스트[성공]")
    void save() {
        // given
        Category category = categoryRepository.save(new Category("cname", "color", "imageUrl", "description"));
        Member member = memberRepository.save(new Member("mname", "mage", Role.USER));
        Product product = productRepository.save(new Product("pname", 1_000, "pimage", category));
        CreateWishRequest request = new CreateWishRequest(product.getId());

        // when
        wishService.save(request, 1, member.getId());
        Wish actual = wishRepository.findByIdFetchJoin(1L).get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getProduct().getId()).isEqualTo(product.getId());
        assertThat(actual.getMember().getId()).isEqualTo(member.getId());
    }
}