package gift.utilForTest;

import gift.domain.entity.Category;
import gift.domain.entity.Member;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.entity.Wish;
import gift.global.util.HashUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.test.util.ReflectionTestUtils;

public class MockObjectSupplier {

    private static final Map<Class<?>, Object> entityMap = new HashMap<>();

    static {
        Category category = new Category("상품권", "#919191", "color.png", "");
        Product product = new Product("testProduct", 5000, "image.png", category);
        Member member = new Member("test@example.com", HashUtil.hashCode("password"), "user");
        Wish wish = new Wish(product, member, 5L);
        Option option = new Option(product, "옵션1", 555);

        ReflectionTestUtils.setField(category, "id", 1L);
        ReflectionTestUtils.setField(product, "id", 1L);
        ReflectionTestUtils.setField(product, "options", new ArrayList<>(List.of(option)));
        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(wish, "id", 1L);
        ReflectionTestUtils.setField(option, "id", 1L);

        entityMap.put(Category.class, category);
        entityMap.put(Product.class, product);
        entityMap.put(Member.class, member);
        entityMap.put(Wish.class, wish);
        entityMap.put(Option.class, option);
    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(entityMap.get(clazz));
    }
}
