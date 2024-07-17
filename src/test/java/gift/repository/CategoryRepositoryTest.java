package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Category;
import gift.exception.NoSuchProductException;
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
    void setup() {
        category = new Category("test", "#FFFFFF", "testImageUrl", "test");
    }

    @DisplayName("카테고리 추가")
    @Test
    void save() {
        // given
        Category expected = category;

        // when
        Category actual = categoryRepository.save(category);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @DisplayName("id로 카테고리 찾기")
    @Test
    void findById() {
        // given
        Category expected = categoryRepository.save(category);

        // when
        Category actual = categoryRepository.findById(expected.getId())
            .orElseThrow(NoSuchProductException::new);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @DisplayName("카테고리 수정")
    @Test
    void update() {
        // given
        long id = categoryRepository.save(category).getId();
        Category expected = new Category(id, "updatedTest", "#000000", "updatedTestImageUrl", "updatedTest");

        // when
        Category actual = categoryRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @DisplayName("카테고리 삭제")
    @Test
    void delete() {
        // given
        long id = categoryRepository.save(category).getId();

        // when
        categoryRepository.deleteById(id);

        // then
        assertThat(categoryRepository.findById(id)).isEmpty();
    }
}
