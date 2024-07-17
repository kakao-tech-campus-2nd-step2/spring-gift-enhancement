package gift;

import gift.controller.CategoryController;
import gift.domain.model.dto.CategoryAddRequestDto;
import gift.domain.model.dto.CategoryResponseDto;
import gift.domain.model.dto.CategoryUpdateRequestDto;
import gift.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CategoryTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoriesTest() {
        // Given
        List<CategoryResponseDto> expectedCategories = Arrays.asList(
            new CategoryResponseDto(1L, "Category 1"),
            new CategoryResponseDto(2L, "Category 2")
        );
        when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        // When
        List<CategoryResponseDto> actualCategories = categoryController.getAllCategories();

        // Then
        assertEquals(expectedCategories, actualCategories);
        verify(categoryService).getAllCategories();
    }

    @Test
    void addCategoryTest() {
        // Given
        CategoryAddRequestDto requestDto = new CategoryAddRequestDto("New Category");
        CategoryResponseDto expectedResponse = new CategoryResponseDto(1L, "New Category");
        when(categoryService.addCategory(requestDto)).thenReturn(expectedResponse);

        // When
        CategoryResponseDto actualResponse = categoryController.addCategory(requestDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(categoryService).addCategory(requestDto);
    }

    @Test
    void updateCategoryTest() {
        // Given
        CategoryUpdateRequestDto requestDto = new CategoryUpdateRequestDto(1L, "Updated Category");
        CategoryResponseDto expectedResponse = new CategoryResponseDto(1L, "Updated Category");
        when(categoryService.updateCategory(requestDto)).thenReturn(expectedResponse);

        // When
        CategoryResponseDto actualResponse = categoryController.updateCategory(requestDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(categoryService).updateCategory(requestDto);
    }

    @Test
    void validDeleteCategoryTest() {
        // Given
        Long categoryId = 1L;
        doNothing().when(categoryService).deleteCategory(categoryId);

        // When & Then
        assertDoesNotThrow(() -> categoryController.deleteCategory(categoryId));
        verify(categoryService).deleteCategory(categoryId);
    }

    @Test
    void invalidDeleteCategoryTest() {
        // Given
        Long categoryId = 1L;
        doThrow(new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."))
            .when(categoryService).deleteCategory(categoryId);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> categoryController.deleteCategory(categoryId));
        verify(categoryService).deleteCategory(categoryId);
    }
}