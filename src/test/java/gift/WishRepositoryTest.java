package gift;

import gift.model.Category;
import gift.model.Member;
import gift.model.Product;
import gift.model.ProductOption;
import gift.model.Wish;
import gift.repository.CategoryRepository;
import gift.repository.MemberRepository;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");
        memberRepository.save(member);

        Category category = new Category();
        category.setName("Food");
        categoryRepository.save(category);

        Product product = new Product();
        product.setName("열라면");
        product.setPrice(1600);
        product.setImageurl("https://i.namu.wiki/i/fuvd7qkb8P6PA_sD5ufjgpKUhRgxxTrIWnkPIg5H_UAPMUaArn1U1DweD7T_f_8RVxTDjqaiFwKr-quURwc_eQ.webp");
        product.setCategory(category);
        productRepository.save(product);

        ProductOption option = new ProductOption();
        option.setName("Default Option");
        option.setQuantity(10);
        option.setProduct(product);
        productOptionRepository.save(option);

        List<ProductOption> options = new ArrayList<>();
        options.add(option);
        product.setOptions(options);
        productRepository.save(product);

        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wish.setProductOption(option);
        Wish savedWish = wishRepository.save(wish);

        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getMember().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findByMemberId() {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");
        memberRepository.save(member);

        Category category = new Category();
        category.setName("Food");
        categoryRepository.save(category);

        Product product = new Product();
        product.setName("열라면");
        product.setPrice(1600);
        product.setImageurl("https://i.namu.wiki/i/fuvd7qkb8P6PA_sD5ufjgpKUhRgxxTrIWnkPIg5H_UAPMUaArn1U1DweD7T_f_8RVxTDjqaiFwKr-quURwc_eQ.webp");
        product.setCategory(category);
        productRepository.save(product);

        ProductOption option = new ProductOption();
        option.setName("Default Option");
        option.setQuantity(10);
        option.setProduct(product);
        productOptionRepository.save(option);

        List<ProductOption> options = new ArrayList<>();
        options.add(option);
        product.setOptions(options);
        productRepository.save(product);

        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wish.setProductOption(option);
        wishRepository.save(wish);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Wish> wishesPage = wishRepository.findByMemberId(member.getId(), pageable);
        List<Wish> wishes = wishesPage.getContent();

        assertThat(wishes).isNotEmpty();
        assertThat(wishes.get(0).getMember().getEmail()).isEqualTo("test@example.com");
    }
}
