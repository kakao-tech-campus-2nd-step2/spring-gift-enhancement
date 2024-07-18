package test;

import gift.entity.Category;
import gift.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class CategoryControllerTest {

    CategoryService categoryService;

    @Test
    void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
        );
        when(categoryService.getAllCategories()).thenReturn(categories);

    }
}
