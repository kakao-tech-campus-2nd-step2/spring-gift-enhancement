package gift.EntityTest;

import gift.Model.Category;
import gift.Model.Member;
import gift.Model.Product;
import gift.Model.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class WishTest {

    private Category category;
    private Member member1;
    private Member member2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void beforEach() {
        category = new Category("식품", "#812f3D", "식품 url", "");
        product1 = new Product("아메리카노", 4000, "아메리카노url", category);
        product2 = new Product("카푸치노", 4500, "카푸치노url",category);
        member1 = new Member("woo6388@naver.com", "12345678");
        member2 = new Member("qoo6388@naver.com", "0000");
    }

    @Test
    void creationTest(){
        Wish wish = new Wish(member1, product1, 1);

        assertAll(
                ()->assertThat(wish.getMember()).isEqualTo(member1),
                ()-> assertThat(wish.getProduct()).isEqualTo(product1),
                ()->assertThat(wish.getCount()).isEqualTo(1)
        );
    }

    @Test
    void setterTest(){
        Wish wish = new Wish(member1, product1, 1);

        wish.setMember(member2);
        wish.setProduct(product2);
        wish.setCount(2);

        assertAll(
                () -> assertThat(wish.getMember()).isEqualTo(member2),
                () -> assertThat(wish.getProduct()).isEqualTo(product2),
                () -> assertThat(wish.getCount()).isEqualTo(2)
        );
    }


}
