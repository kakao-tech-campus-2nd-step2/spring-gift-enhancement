package gift.repository;

import gift.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("카테고리 레포지토리 단위테스트")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 이름 중복 확인")
    void existsByName() {
        //Given
        Category category = new Category("test", "test", "test", "test");
        categoryRepository.save(category);

        //When
        boolean resultBoolean = categoryRepository.existsByName("test");

        //Then
        assertThat(resultBoolean).isTrue();
    }
}
