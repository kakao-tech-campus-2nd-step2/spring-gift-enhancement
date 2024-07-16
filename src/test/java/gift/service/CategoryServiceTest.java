package gift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gift.dto.category.CategoryResponse;
import gift.model.Category;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    @DisplayName("모든 카테고리 조회")
    public void testGetAllCategories() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponse> categories = categoryService.getAllCategories();
        assertEquals(1, categories.size());
        assertEquals("Category", categories.getFirst().name());
    }

    @Test
    @DisplayName("카테고리 ID로 조회")
    public void testGetCategoryById() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse categoryResponse = categoryService.getCategoryById(1L);
        assertEquals("Category", categoryResponse.name());
    }
}
