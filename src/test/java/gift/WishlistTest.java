package gift;

import gift.entity.Category;
import gift.entity.Product;
import gift.entity.Wishlist;
import gift.entity.Member;
import gift.repository.CategoryRepository;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class WishlistTest {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;


    private Wishlist testWishlist;
    private Member testMember;
    private Product testProduct;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category(1, "test", "test", "test", "test");
        testProduct = new Product(1, testCategory, 1, "test", "test");
        testMember = new Member(1, "test", "test", "test");
        categoryRepository.save(testCategory);
        productRepository.save(testProduct);
        memberRepository.save(testMember);

        testWishlist = new Wishlist(testMember, testProduct, 1);
    }

    @AfterEach
    public void tearDown() {
        wishlistRepository.deleteAll();
    }

    @Test
    void testAddWishlist() {
        wishlistRepository.save(testWishlist);
        assertEquals(1, wishlistRepository.findAll().size());
    }
}
