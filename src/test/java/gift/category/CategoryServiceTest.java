package gift.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.category.model.CategoryRepository;
import gift.category.model.dto.Category;
import gift.category.model.dto.CategoryRequest;
import gift.category.model.dto.CategoryResponse;
import gift.category.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category defaultCategory;
    private CategoryRequest defaultRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultCategory = new Category("기본", "기본 카테고리");
        defaultRequest = new CategoryRequest("수정/추가 카테고리", "새 카테고리입니다");
    }

    /* 테스트 네이밍 : 메소드명_기대결과_테스트상태 */
    @Test
    void findCategoryById_ReturnCategoryResponse_categoryExists() {
        // given
        when(categoryRepository.findByIdAndIsActiveTrue(defaultCategory.getId())).thenReturn(
                Optional.of(defaultCategory));

        // when
        CategoryResponse response = categoryService.findCategoryById(defaultCategory.getId());

        // then
        assertNotNull(response);
        assertEquals(defaultCategory.getName(), response.getName());
        verify(categoryRepository, times(1)).findByIdAndIsActiveTrue(defaultCategory.getId());
    }

    @Test
    void findCategoryById_ThrowEntityNotFoundException_CategoryDoesNotExist() {
        // given
        Long missingId = 99L;
        when(categoryRepository.findByIdAndIsActiveTrue(missingId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(missingId));
        verify(categoryRepository, times(1)).findByIdAndIsActiveTrue(missingId);
    }

    @Test
    void findAllCategories_ReturnListOfCategoryResponses() {
        // given
        Category anotherCategory = new Category("2번 카테고리", "2번입니다");
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(defaultCategory, anotherCategory));

        // when
        List<CategoryResponse> responses = categoryService.findAllCategories();

        // then
        assertEquals(2, responses.size());
        assertEquals(defaultCategory.getName(), responses.get(0).getName());
        assertEquals(anotherCategory.getName(), responses.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void addCategory_SaveDB() {
        // given, when
        categoryService.addCategory(defaultRequest);

        // then
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_CategoryExists() {
        // given
        when(categoryRepository.findByIdAndIsActiveTrue(defaultCategory.getId())).thenReturn(
                Optional.of(defaultCategory));

        // when
        categoryService.updateCategory(defaultCategory.getId(), defaultRequest);

        // then
        assertEquals(defaultRequest.name(), defaultCategory.getName());
        assertEquals(defaultRequest.description(), defaultCategory.getDescription());
        verify(categoryRepository, times(1)).save(defaultCategory);
    }

    @Test
    void updateCategory_ThrowEntityNotFoundException_CategoryDoesNotExist() {
        // given
        Long missingId = 99L;
        when(categoryRepository.findByIdAndIsActiveTrue(missingId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(missingId, defaultRequest));
        verify(categoryRepository, times(1)).findByIdAndIsActiveTrue(missingId);
    }

    @Test
    void deleteCategory_UpdateCategoryAsInactive() {
        // given
        when(categoryRepository.findByIdAndIsActiveTrue(defaultCategory.getId())).thenReturn(
                Optional.of(defaultCategory));

        // when
        categoryService.deleteCategory(defaultCategory.getId());

        // then
        assertFalse(defaultCategory.isActive());
        verify(categoryRepository, times(1)).save(defaultCategory);
    }

    @Test
    void deleteCategory_ThrowEntityNotFoundException_CategoryDoesNotExist() {
        // given
        Long missingId = 99L;
        when(categoryRepository.findByIdAndIsActiveTrue(missingId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(missingId));
        verify(categoryRepository, times(1)).findByIdAndIsActiveTrue(missingId);
    }
}
