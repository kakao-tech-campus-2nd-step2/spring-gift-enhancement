package gift.doamin;

import gift.domain.Category;
import gift.domain.Product;
import gift.domain.User;
import gift.domain.WishProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomainTest {
    @Test
    void product_test(){
        Category category = new Category("물품");
        Product product = new Product("name", 4500, "none", category);
        Assertions.assertThat(product).isNotNull();

        product.updateEntity("name_", 9000, "url", category);
        Assertions.assertThat(product.getName()).isEqualTo("name_");
        Assertions.assertThat(product.getPrice()).isEqualTo(9000);
        Assertions.assertThat(product.getImageUrl()).isEqualTo("url");
        Assertions.assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    void user_test(){
        User user = new User("Bae", "Bae@email.com", "aaaa");
        Assertions.assertThat((user)).isNotNull();

        user.updateEntity("Bae1@gamil.com", "aaaaa");
        Assertions.assertThat(user.getUserId()).isEqualTo("Bae");
        Assertions.assertThat(user.getEmail()).isEqualTo("Bae1@gamil.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("aaaaa");
    }

    @Test
    void wish_test(){
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
}
