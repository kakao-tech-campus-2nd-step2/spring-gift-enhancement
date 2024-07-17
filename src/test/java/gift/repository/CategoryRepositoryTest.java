package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.domain.category.dto.CategoryRequest;
import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        CategoryRequest request = new CategoryRequest("test", "color", "imageUrl", "description");
        
        Category expected = categoryRepository.save(dtoToEntity(request));

        // when
        Category actual = categoryRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        CategoryRequest request = new CategoryRequest("test", "color", "imageUrl", "description");
        
        Category expected = dtoToEntity(request);

        // when
        Category actual = categoryRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        CategoryRequest request = new CategoryRequest("test", "color", "imageUrl", "description");
        Category savedCategory = categoryRepository.save(dtoToEntity(request));

        // when
        categoryRepository.delete(savedCategory);

        // then
        assertTrue(categoryRepository.findById(savedCategory.getId()).isEmpty());
    }

    private Category dtoToEntity(CategoryRequest request) {
        return new Category(request.getName(), request.getColor(), request.getImageUrl(), request.getDescription());
    }
}
