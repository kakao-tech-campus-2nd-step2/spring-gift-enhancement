package gift.wish;

import gift.model.member.Member;
import gift.model.product.Category;
import gift.model.product.Product;
import gift.model.product.ProductName;
import gift.model.wish.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WishTest {
    private Wish originWish;
    private Member expectedMember;
    private Product expectedProduct;
    private Category category;


    @BeforeEach
    public void setUp() {
        expectedMember = new Member("qwer@gmail.com","1234","root");
        category = new Category("category1");
        expectedProduct = new Product(category,new ProductName("product1"),1000,"qwer.com",1000);
        originWish = new Wish(expectedProduct,expectedMember,1000 );
    }

    @Test
    public void updateWishTest(){
        Wish newWish = new Wish(expectedProduct, expectedMember,2000);
        originWish.updateWish(newWish);

        assertEquals(2000, originWish.getAmount());
    }
}
