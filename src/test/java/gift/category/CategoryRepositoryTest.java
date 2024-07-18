package gift.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import gift.product.Product;
import gift.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setCategoryRepository() {
        categoryRepository.save(new Cateogory("교환권","쌈@뽕한 블루","www","여름"));
        categoryRepository.save(new Cateogory("과제면제권","방학","www.com","학교"));
        categoryRepository.save(new Cateogory("라우브","스틸더","www.show","키야"));
    }

//    @BeforeEach
//    void setProductRepository() {
//        productRepository.save(new Product("사과", 2000, "www",));
//        productRepository.save(new Product("참외",4000,"달다!",1));
//        productRepository.save(new Product("바나나", 3000,"맛있다!",2));
//    }

    @Test
    @DisplayName("카테고리 저장 테스트")
    void save() {
        Cateogory expected = new Cateogory("교환권","쌈@뽕한 블루","www","여름");
        Cateogory actual = categoryRepository.save(expected);

        assertAll(
            () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("단일 카테고리 조회 테스트")
    void findById() {
        Cateogory expected = new Cateogory("교환권","쌈@뽕한 블루","www","여름");
        categoryRepository.save(expected);
        Cateogory actual = categoryRepository.findById(expected.getId()).get();

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo("교환권"),
            () -> assertThat(actual.getColor()).isEqualTo("쌈@뽕한 블루"),
            () -> assertThat(actual.getImageUrl()).isEqualTo("www"),
            () -> assertThat(actual.getDescription()).isEqualTo("여름")
        );
    }

    @Test
    @DisplayName("모든 카테고리 조회 테스트")
    void findAll() {
        Cateogory cateogory1 = new Cateogory("교환권","쌈@뽕한 블루","www","여름");
        Cateogory cateogory2 = new Cateogory("과제면제권","방학","www.com","학교");
        Cateogory cateogory3 = new Cateogory("라우브","스틸더","www.show","키야");
        categoryRepository.save(cateogory1);
        categoryRepository.save(cateogory2);
        categoryRepository.save(cateogory3);

        List<Cateogory> categoryList = categoryRepository.findAll();

        assertAll(
            ()-> assertThat(categoryList.size()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void update() {
        Cateogory category = new Cateogory("교환권","쌈@뽕한 블루","www","여름");

        category.update("과제면제권","방학","www.com","학교");

        assertAll(
            () -> assertThat(category.getName()).isEqualTo("과제면제권"),
            () -> assertThat(category.getColor()).isEqualTo("방학"),
            () -> assertThat(category.getImageUrl()).isEqualTo("www.com"),
            () -> assertThat(category.getDescription()).isEqualTo("학교")
        );
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void delete() {
        Cateogory category = new Cateogory("교환권","쌈@뽕한 블루","www","여름");
        categoryRepository.save(category);
        categoryRepository.deleteById(category.getId());

        List<Cateogory> isCategory = categoryRepository.findById(category.getId()).stream().toList();

        assertAll(
            () -> assertThat(isCategory.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("카테고리 속 상품 조회 테스트")
    void findProductInCategory() {
        Cateogory cateogory = new Cateogory("교환권","쌈@뽕한 블루","www","여름");
        categoryRepository.save(cateogory);
        productRepository.save(new Product("사과", 2000, "www", cateogory));
        productRepository.save(new Product("참외",4000,"달다!", cateogory));

        List<Product> productList = productRepository.findAllByCateogory_Id(cateogory.getId());

        assertAll(
            () -> assertThat(productList.size()).isEqualTo(2)
        );
    }

}
