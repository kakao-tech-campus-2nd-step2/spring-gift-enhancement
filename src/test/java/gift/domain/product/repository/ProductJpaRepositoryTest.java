package gift.domain.product.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.product.entity.Category;
import gift.domain.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;


    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product expected = new Product(null, category, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        // when
        Product actual = productJpaRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("전체 상품 조회 테스트")
    void findAll() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product expected = new Product(null, category, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        productJpaRepository.save(expected);

        // when
        List<Product> productList = productJpaRepository.findAll();

        // then
        assertAll(
            () -> assertThat(productList.size()).isEqualTo(4),
            () -> assertThat(productList.get(3).getName()).isEqualTo(expected.getName()),
            () -> assertThat(productList.get(3).getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(productList.get(3).getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("ID로 상품 조회 테스트")
    void findById() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product expected = new Product(null, category, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        productJpaRepository.save(expected);

        // when
        Product actual = productJpaRepository.findById(expected.getId()).get();

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product expected = new Product(null, category, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product saved = productJpaRepository.save(expected);

        // when
        productJpaRepository.delete(saved);

        // then
        Optional<Product> deletedProduct = productJpaRepository.findById(saved.getId());
        assertThat(deletedProduct).isEmpty();
    }
}