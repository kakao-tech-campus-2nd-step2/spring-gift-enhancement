package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import gift.entity.Category;
import gift.repository.CategoryRepository;
import gift.service.CategoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void findAllTest(){

        Category category1 = new Category("교환권");
        Category category2 = new Category("기프티콘");
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.findAll();

        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).getName()).isEqualTo("교환권");
        assertThat(categories.get(1).getName()).isEqualTo("기프티콘");
    }

    @Test
    public void findByNameTest(){
        Long id = 1L;
        Category category = new Category("교환권");
        category.setId(id);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Optional<Category> categoryOp = categoryService.findById(id);

        assertThat(categoryOp).isPresent();
        assertThat(categoryOp.get().getId()).isEqualTo(id);

    }

    @Test
    public void saveTest(){
        Category category = new Category("교환권");
        category.setId(1L);
        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.save(category);

        assertThat(savedCategory.getId()).isEqualTo(1L);
        assertThat(savedCategory.getName()).isEqualTo("교환권");

    }

    @Test
    public void ExistsByNameTest(){
        String name = "교환권";
        when(categoryRepository.existsByName(name)).thenReturn(true);

        boolean exists = categoryService.existsByName(name);

        assertThat(exists).isTrue();

    }

}
