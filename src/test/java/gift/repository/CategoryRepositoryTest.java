package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.category.Category;
import gift.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    void beforeEach() {
        category = new Category("상품권", "#ff11ff", "image.jpg", "money");
    }

    @Test
    @DisplayName("카테고리 추가 테스트")
    void save() {
        //Given

        //When
        Category actual = categoryRepository.save(category);

        //Then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("상품권");
        assertThat(actual.getColor()).isEqualTo("#ff11ff");
        assertThat(actual.getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getDescription()).isEqualTo("money");
    }

    @Test
    @DisplayName("카테고리에 있는 이름을 다시 추가할 때 DB에서 오류")
    void saveSameNameError() {
        //Given
        categoryRepository.save(category);
        Category category1 = new Category("상품권", "#ff11ff", "image.jpg", "money");

        //When

        //Then
        assertThatThrownBy(() -> {
            categoryRepository.save(category1);
        });
    }

    @Test
    @DisplayName("카테고리 전부 찾기")
    void findAll() {
        //Given
        categoryRepository.save(category);
        Category category1 = new Category("가전", "#ddff11", "image.jpg", "");
        categoryRepository.save(category1);

        //When
        List<Category> actual = categoryRepository.findAll();

        //Then
        assertThat(actual).hasSize(2);
        assertThat(actual.getFirst().getId()).isNotNull();
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual.getFirst().getName()).isEqualTo("상품권");
        assertThat(actual.getFirst().getColor()).isEqualTo("#ff11ff");
        assertThat(actual.getFirst().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getFirst().getDescription()).isEqualTo("money");
        assertThat(actual.get(1).getName()).isEqualTo("가전");
        assertThat(actual.get(1).getColor()).isEqualTo("#ddff11");
        assertThat(actual.get(1).getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.get(1).getDescription()).isEqualTo("");
    }

    @Test
    @DisplayName("카테고리 아이디로 찾기 테스트")
    void findById() {
        //Given
        categoryRepository.save(category);

        //When
        Optional<Category> actual = categoryRepository.findById(category.getId());
        assertThat(actual).isPresent();

        //Then
        assertThat(actual.get().getName()).isEqualTo("상품권");
        assertThat(actual.get().getColor()).isEqualTo("#ff11ff");
        assertThat(actual.get().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.get().getDescription()).isEqualTo("money");
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteById() {
        //Given
        categoryRepository.save(category);

        //When
        categoryRepository.deleteById(category.getId());
        Optional<Category> actual = categoryRepository.findById(category.getId());

        //Then
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("카테고리 이름으로 찾기")
    void existsByName() {
        //Given
        categoryRepository.save(category);

        //When
        Boolean actual = categoryRepository.existsByName(category.getName());

        //Then
        assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName("존재하지 않는 이름으로 카테고리를 찾으면 false 리턴")
    void notExistsByName() {
        //Given
        categoryRepository.save(category);

        //When
        Boolean actual = categoryRepository.existsByName("이춘식");

        //Then
        assertThat(actual).isEqualTo(false);
    }
}
