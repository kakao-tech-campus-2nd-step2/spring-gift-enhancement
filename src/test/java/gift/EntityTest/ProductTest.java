package gift.EntityTest;

import gift.Model.Category;
import gift.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductTest {

    private Category category;
    private Category category2;

    @BeforeEach
    void beforeEach(){
        category = new Category("식품", "#812f3D", "식품 url", "");
        category2 = new Category("음료", "#732d2b", "음료 url", "");
    }

    @Test
    void creationTest() {
        Product product= new Product("아메리카노", 4000, "아메리카노url",category);
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("아메리카노"),
                () -> assertThat(product.getPrice()).isEqualTo(4000),
                () -> assertThat(product.getImageUrl()).isEqualTo("아메리카노url"),
                ()-> assertThat(product.getCategory()).isEqualTo(category)
        );
    }

    @Test
    void setterTest() {
        Product product = new Product("아메리카노", 4000, "아메리카노url", category);
        product.setName("카푸치노");
        product.setPrice(5000);
        product.setImageUrl("카푸치노url");
        product.setCategory(category2);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("카푸치노"),
                () -> assertThat(product.getPrice()).isEqualTo(5000),
                () -> assertThat(product.getImageUrl()).isEqualTo("카푸치노url"),
                () -> assertThat(product.getCategory()).isEqualTo(category2)
        );
    }
}
