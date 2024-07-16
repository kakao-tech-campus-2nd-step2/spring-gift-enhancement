package gift.category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gift.category.model.CategoryRepository;
import gift.category.model.dto.Category;
import java.util.List;
import java.util.Optional;
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
        categoryRepository.save(new Category(2L, "22", "2222", true));
    }

    @Test
    public void testFindActiveCategoryList() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        assertEquals(2, categories.size());
    }

    @Test
    public void testFindActiveCategoryById() {
        Optional<Category> optionalResult = categoryRepository.findByIdAndIsActiveTrue(1L);

        optionalResult.isPresent();
        Category category1 = optionalResult.get();

        assertEquals(category1.getName(), category.getName());
    }

    @Test
    public void testSaveAndDelete() {

    }

}
