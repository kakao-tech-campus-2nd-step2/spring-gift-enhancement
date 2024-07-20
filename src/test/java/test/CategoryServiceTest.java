package test;

import gift.entity.Category;
import gift.repository.CategoryRepository;
import gift.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@DataJpaTest
public class CategoryServiceTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepository;

    @Test
    void testGetAllCategories() {
        List<Category> categories = Arrays.asList(
                categoryRepository.findCategoryByName("식품"),
                categoryRepository.findCategoryByName("패션")
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("식품", result.get(0).getName());
        assertEquals("패션", result.get(1).getName());
    }
}
