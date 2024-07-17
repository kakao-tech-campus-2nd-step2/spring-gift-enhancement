package gift.service;

import gift.domain.Category;
import gift.dto.request.CategoryRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.DuplicateCategoryNameException;
import gift.repository.category.CategorySpringDataJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategorySpringDataJpaRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private List<Category> mockCategories;

    @BeforeEach
    public void setUp() {
        mockCategories = new ArrayList<>();
        mockCategories.add(new Category(1L, "교환권"));
        mockCategories.add(new Category(2L, "상품권"));
    }

    @Test
    public void testGetCategories() {
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> categories = categoryService.getCategories();

        assertEquals(2, categories.size());
        assertEquals("교환권", categories.get(0).getName());
        assertEquals("상품권", categories.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Category mockCategory = new Category(categoryId, "교환권");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        Category category = categoryService.getCategory(categoryId);

        assertEquals("교환권", category.getName());

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        Long categoryId = 11L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategory(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testCreateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("뷰티");
        when(categoryRepository.existsByName(categoryRequest.getName())).thenReturn(false);

        Category createdCategory = categoryService.createCategory(categoryRequest);

        assertEquals("뷰티", createdCategory.getName());

        verify(categoryRepository, times(1)).existsByName("뷰티");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testCreateCategoryDuplicateName() {
        CategoryRequest categoryRequest = new CategoryRequest("뷰티");
        when(categoryRepository.existsByName(categoryRequest.getName())).thenReturn(true);

        assertThrows(DuplicateCategoryNameException.class, () -> categoryService.createCategory(categoryRequest));

        verify(categoryRepository, times(1)).existsByName("뷰티");
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("뷰티");
        Category existingCategory = new Category(categoryId, "교환권");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByName(categoryRequest.getName())).thenReturn(false);

        Category updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);

        assertEquals("뷰티", updatedCategory.getName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).existsByName("뷰티");
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testUpdateCategoryNotFound() {
        Long categoryId = 11L;
        CategoryRequest categoryRequest = new CategoryRequest("뷰티");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryRequest));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).existsByName(any());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void testUpdateCategoryDuplicateName() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("상품권");
        Category existingCategory = new Category(categoryId, "교환권");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByName(categoryRequest.getName())).thenReturn(true);

        assertThrows(DuplicateCategoryNameException.class, () -> categoryService.updateCategory(categoryId, categoryRequest));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).existsByName("상품권");
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;
        Category existingCategory = new Category(categoryId, "패션");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(existingCategory);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        Long categoryId = 11L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any());
    }
}
