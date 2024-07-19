package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import gift.Model.Category;
import gift.Repository.CategoryRepository;
import gift.Repository.ProductRepository;
import gift.Service.CategoryService;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @Test
    void getAllCategories() {
        // Given
        Category category1 = new Category(20L, "testCategory1", "testColor1", "testUrl1",
            "testDescription1", null);// 1~14번까지 기존 데이터가 존재하므로 20으로 test객체 생성

        Category category2 = new Category(21L, "testCategory2", "testColor2", "testUrl2",
            "testDescription2", null);// 1~14번까지 기존 데이터가 존재하므로 21으로 test객체 생성

        List<Category> expect = Arrays.asList(category1, category2);

        // When
        Mockito.when(categoryService.getAllCategory()).thenReturn(expect);

        // Then
        List<Category> actual = categoryService.getAllCategory();

        assertEquals(expect, actual);// 검증


    }
    @Test
    void addCategory(){
        Category expect = new Category(20L, "testCategory1", "testColor1", "testUrl1",
            "testDescription1", null);// 1~14번까지 기존 데이터가 존재하므로 20으로 test객체 생성

        // When
        Mockito.when(categoryService.addCategory(expect)).thenReturn(expect);

        // Then
        Category actual = categoryService.addCategory(expect);

        assertEquals(expect, actual);// 검증
    }

    @Test
    void updateCategory(){

        Category expect = new Category(1L, "testCategory2", "testColor2", "testUrl2",
            "testDescription2", null);

        // When
        Mockito.when(categoryService.updateCategory(expect)).thenReturn(expect);

        // Then
        Category actual = categoryService.updateCategory(expect);

        assertEquals(expect, actual);// 검증
    }

}
