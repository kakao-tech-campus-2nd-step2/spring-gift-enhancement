package gift.doamin;

import gift.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomainTest {
    @Test
    void productTest(){
        Category category = new Category("물품");
        Product product = new Product("name", 4500, "none", category);
        Assertions.assertThat(product).isNotNull();

        Option option = new Option("[1] 아메리카노", 300L);
        product.addOption(option);

        product.updateEntity("name_", 9000, "url", category);
        Assertions.assertThat(product.getName()).isEqualTo("name_");
        Assertions.assertThat(product.getPrice()).isEqualTo(9000);
        Assertions.assertThat(product.getImageUrl()).isEqualTo("url");
        Assertions.assertThat(product.getCategory()).isEqualTo(category);
        Assertions.assertThat(product.getOptions().getFirst()).isEqualTo(option);
    }

    @Test
    void userTest(){
        User user = new User("Bae", "Bae@email.com", "aaaa");
        Assertions.assertThat((user)).isNotNull();

        user.updateEntity("Bae1@gamil.com", "aaaaa");
        Assertions.assertThat(user.getUserId()).isEqualTo("Bae");
        Assertions.assertThat(user.getEmail()).isEqualTo("Bae1@gamil.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("aaaaa");
    }

    @Test
    void wishTest(){
        Category category = new Category("물품");
        Product product = new Product("name", 4000,"none", category);
        User user = new User("bae", "Bae@email.com", "aaaa");
        WishProduct wishProduct = new WishProduct(user, product);
        Assertions.assertThat(wishProduct).isNotNull();
        Assertions.assertThat(wishProduct.getCount()).isEqualTo(1);

        wishProduct.changeCount(2);
        Assertions.assertThat(wishProduct.getProduct()).isEqualTo(product);
        Assertions.assertThat(wishProduct.getUser()).isEqualTo(user);
        Assertions.assertThat(wishProduct.getCount()).isEqualTo(2);
    }

    @Test
    void categoryTest(){
        Category category = new Category("물품");
        Assertions.assertThat(category).isNotNull();

        category.update("신규");
        Assertions.assertThat(category.getName()).isEqualTo("신규");
    }

    @Test
    void optionTest(){
        Option option = new Option("[1] 아시아", 23L);
        Assertions.assertThat(option).isNotNull();

        option.update("[1] 아메리카", 22L);
        Assertions.assertThat(option.getName()).isEqualTo("[1] 아메리카");
        Assertions.assertThat(option.getQuantity()).isEqualTo(22L);
    }
}
