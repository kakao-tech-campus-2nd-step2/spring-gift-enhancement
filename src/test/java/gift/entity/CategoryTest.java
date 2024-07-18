package gift.entity;

import gift.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("카테고리 엔티티 테스트")
class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("Name Null")
    void nameNull() {
        Category category = new Category(null, "color", "imageUrl", "description");

        assertThatThrownBy(() -> categoryRepository.save(category)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("imageUrl Null")
    void imageNull() {
        Category category = new Category("hello", "color", null, "description");

        assertThatThrownBy(() -> categoryRepository.save(category)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Name, Color Null")
    void nameColorNull() {
        Category category = new Category(null, null, "imageUrl", "description");

        assertThatThrownBy(() -> categoryRepository.save(category)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Color 길이 8일때")
    void ColorLength() {
        Category category = new Category("name", "A".repeat(8), "imageUrl", "description");

        assertThatThrownBy(() -> categoryRepository.save(category)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("이름 Unique")
    void nameUnique() {
        Category category1 = new Category("name", "color", "imageUrl", "description");
        Category category2 = new Category("name", "color", "imageUrl", "description");
        categoryRepository.save(category1);
        assertThatThrownBy(() -> categoryRepository.save(category2)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }



}