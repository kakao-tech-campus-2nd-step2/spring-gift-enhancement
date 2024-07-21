package gift.service;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.CategoryRequest;
import gift.entity.Category;
import gift.entity.Member;
import gift.repository.CategoryRepository;
import gift.repository.MemberRepository;
import gift.entity.Product;
import gift.repository.ProductRepository;
import gift.dto.WishResponse;
import gift.entity.Wish;
import gift.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@DataJpaTest
class WishServiceTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private WishService wishService;
    private CategoryService categoryService;

    private Member member;
    private Product product1;
    private Product product2;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
        member = new Member();
        member.setEmail("user@example.com");
        member.setPassword("password");
        member = memberRepository.save(member);

        CategoryRequest categoryRequest = new CategoryRequest("교환권","#FFFFFF","http://example.com/category1.jpg","");
        category = categoryService.addCategory(categoryRequest);

        product1 = new Product("Sample Product 1", 100, "http://example.com/product1.jpg", category);
        product2 = new Product("Sample Product 2", 200, "http://example.com/product2.jpg", category);
        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        wishRepository.save(new Wish(member, product1));
        wishRepository.save(new Wish(member, product2));

        wishService = new WishService(wishRepository, productRepository);
    }

    @Test
    void testGetWishesPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        Slice<WishResponse> wishesPage = wishService.getWishes(member, pageable).map(WishResponse::from);

        assertThat(wishesPage).isNotNull();
        assertThat(wishesPage.getContent()).hasSize(2);
        assertThat(wishesPage.getContent().get(0).getProduct().getName()).isEqualTo(
            "Sample Product 1");
        assertThat(wishesPage.getContent().get(1).getProduct().getName()).isEqualTo(
            "Sample Product 2");
    }

}