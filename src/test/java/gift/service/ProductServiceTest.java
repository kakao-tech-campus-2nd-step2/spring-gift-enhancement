package gift.service;

import gift.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProductService productService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("product가 삭제되었을 때 product_wishlist에서 해당 행이 삭제되어야 함")
    void productDeleteCascadeWishlistTest() {
        // given
        String testEmail = "test@gmail.com";
        Category category = categoryService.save(new CategoryDTO("test", "#test", "test.com", ""));
        Product product = productService.save(new ProductDTO("test", 123, "test.com", category.getId()));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product.getId()));

        // when
        productService.delete(product.getId());

        // then
        Page<Product> products = wishlistService.getWishlistProducts(testEmail, PageRequest.of(0, 10));
        assertThat(products).hasSize(0);
    }

    @Test
    @DisplayName("특정 product를 wishlist에 담은 유저의 수 확인 테스트")
    void productWishlistCheckTest() {
        // given
        String testEmail1 = "test1@gmail.com";
        String testEmail2 = "test2@gmail.com";
        Category category = categoryService.save(new CategoryDTO("test", "#test", "test.com", ""));
        Product product = productService.save(new ProductDTO("test", 123, "test.com", category.getId()));

        // when
        wishlistService.addWishlistProduct(testEmail1, new WishlistDTO(product.getId()));
        wishlistService.addWishlistProduct(testEmail2, new WishlistDTO(product.getId()));

        // then
        List<Wishlist> wishlists = productService.getProductWishlist(product.getId());
        assertThat(wishlists).hasSize(2);
    }
}
