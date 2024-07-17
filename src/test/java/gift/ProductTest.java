package gift;

import gift.model.Product;
import gift.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

  @Test
  public void testProductGettersAndSetters() {
    Category category = new Category("Test Category", "#FFFFFF", "http://example.com/category.jpg", "Test Description");

    Product product = new Product("아이스 카페 아메리카노 T", 4500,"https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", category);

    assertEquals("아이스 카페 아메리카노 T", product.getName());
    assertEquals(4500, product.getPrice());
    assertEquals("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", product.getImageUrl());
    assertEquals(category, product.getCategory());
  }
}
