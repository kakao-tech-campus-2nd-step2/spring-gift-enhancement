package gift.util;

import gift.domain.Category;
import gift.domain.Product;

public class ProductFixture {

    public static Product createProduct(Category category) {
        return createProduct("아이스 아메리카노", category);
    }

    public static Product createProduct(String name, Category category) {
        return new Product(name, 4500, "image", category);
    }
}
