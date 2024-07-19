package gift.utilForTest;

import gift.domain.entity.Category;
import gift.domain.entity.Member;
import gift.domain.entity.Product;
import gift.domain.entity.Wish;
import gift.global.util.HashUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.test.util.ReflectionTestUtils;

public class MockObjectSupplier {

    private static final Map<Class<?>, Object> entityMap = new HashMap<>();

    static {
        Category category = new Category("상품권", "#919191", "color.png", "");
        Product product = new Product("testProduct", 5000, "image.png", category);
        Member member = new Member("test@example.com", HashUtil.hashCode("password"), "user");
        Wish wish = new Wish(product, member, 5L);

        ReflectionTestUtils.setField(category, "id", 1L);
        ReflectionTestUtils.setField(product, "id", 1L);
        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(wish, "id", 1L);

        entityMap.put(Category.class, category);
        entityMap.put(Product.class, product);
        entityMap.put(Member.class, member);
        entityMap.put(Wish.class, wish);
    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(entityMap.get(clazz));
    }
}
