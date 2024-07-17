package gift.global.dataLoader;

import gift.domain.Category.Category;
import gift.domain.Category.JpaCategoryRepository;
import gift.domain.cartItem.CartItem;
import gift.domain.cartItem.JpaCartItemRepository;
import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.domain.user.JpaUserRepository;
import gift.domain.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // 테스트 환경에서는 동작하지 않도록
public class DataLoader {

    private final JpaProductRepository jpaProductRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final JpaCartItemRepository jpaCartItemRepository;

    @Autowired
    public DataLoader(
        JpaProductRepository jpaProductRepository,
        JpaUserRepository jpaUserRepository,
        JpaCartItemRepository jpaCartItemRepository,
        JpaCategoryRepository jpaCategoryRepository
    ) {
        this.jpaProductRepository = jpaProductRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.jpaCartItemRepository = jpaCartItemRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @PostConstruct
    public void init() {
        // Category
        Category ethiopia = jpaCategoryRepository.saveAndFlush(new Category("에티오피아산", "에티오피아 산 원두를 사용했습니다."));
        Category jamaica = jpaCategoryRepository.saveAndFlush(new Category("자메이카산", "자메이카산 원두를 사용했습니다."));
        System.out.println("ethiopia = " + ethiopia);
        System.out.println("jamaica = " + jamaica);

        // Product
        Product americano = new Product("아이스 아메리카노 T", ethiopia, 4500,
            "https://example.com/image.jpg");
        Product cafuchino = new Product("아이스 카푸치노 M", jamaica, 4700,
            "https://example.com/image.jpg");
        Product malcha = new Product("핫 말차라떼 L", ethiopia, 6800,
            "https://example.com/image.jpg");
        jpaProductRepository.save(americano);
        jpaProductRepository.save(cafuchino);
        jpaProductRepository.save(malcha);

        // dummy Product data
        for (int i = 0; i < 100; i++) {
            Product dummyProduct = new Product(
                "더미 커피 " + (i + 1),
                ethiopia,
                1000 + (i * 10),
                "https://example.com/dummy" + (i + 1) + ".jpg"
            );
            jpaProductRepository.save(dummyProduct);
        }

        // User
        User minji = new User("minji@example.com", "password1");
        User junseo = new User("junseo@example.com", "password2");
        User donghyun = new User("donghyun@example.com", "password3");
        jpaUserRepository.save(minji);
        jpaUserRepository.save(junseo);
        jpaUserRepository.save(donghyun);

        // CartItem
        jpaCartItemRepository.save(new CartItem(minji, malcha));
        jpaCartItemRepository.save(new CartItem(junseo, cafuchino));
        jpaCartItemRepository.save(new CartItem(donghyun, cafuchino));

    }
}