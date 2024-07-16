package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.model.Category;
import gift.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        Category expected = new Category(null, "TestCategory");
        Category actual = categoryRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findById() {
        Category expected = new Category(null, "TestCategory");
        categoryRepository.save(expected);
        Optional<Category> actual = categoryRepository.findById(expected.getId());
        assertTrue(actual.isPresent());
        assertThat(actual.get().getName()).isEqualTo(expected.getName());
    }

    @Test
    void findAll() {
        Category category1 = new Category(null, "TestCategory1");
        Category category2 = new Category(null, "TestCategory2");
        categoryRepository.saveAll(Arrays.asList(category1, category2));

        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).getName()).isEqualTo("TestCategory1");
        assertThat(categories.get(1).getName()).isEqualTo("TestCategory2");
    }
}
