package gift.main.repository;
import gift.main.dto.CategoryRequest;
import gift.main.entity.Category;
import gift.main.entity.Product;
import gift.main.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 카테고리정상수정조회() {
        Category category = categoryRepository.findByName("패션").get();
        Product product = productRepository.save(new Product("반바지", 1200, "url", new User("유저", "123", "123", "ADMIN"), category));

        category.updateCategory(new CategoryRequest(308, "의류"));

        categoryRepository.save(category);

        Assertions.assertEquals("의류", productRepository.findById(product.getId()).get().getCategoryName());
        System.out.println("productRepository.findById(product.getId()).get().getCategoryName() = " + productRepository.findById(product.getId()).get().getCategoryName());

    }


    @Test
    public void 카테고리삭제() {
        //cascade = CascadeType.DETACH 옵션 사용으로 추가 로직을 통해 삭제해야할 것 같음
        User user = userRepository.save(new User("유저", "123", "123", "ADMIN"));
        Category category = categoryRepository.findByName("패션").get();
        Product product = productRepository.save(new Product("반바지", 1200, "url",user , category));

        Long id = category.getId();
        categoryRepository.delete(category);

//        Assertions.assertFalse(categoryRepository.existsById(category.getId()));
        Assertions.assertFalse(categoryRepository.existsById(id));


    }

}