package gift.category;

import gift.category.model.CategoryRepository;
import gift.category.model.dto.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(1L, "교환권", "모바일 교환권입니다.", true);
        categoryRepository.save(category);
    }

    @Test
    public void testFindActiveCategoryList() {

    }

    @Test
    public void testFindActiveCategoryById() {

    }

    @Test
    public void testSaveAndDelete() {

    }

}
