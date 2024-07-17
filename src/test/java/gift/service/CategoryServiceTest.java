package gift.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.domain.Category;
import gift.dto.CategoryDTO;
import gift.repository.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setup() {
        category = new Category(1L, "test", "#FFFFFF", "testImageUrl", "test");
    }

    @DisplayName("카테고리 추가")
    @Test
    void addCategory() {
        // given
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        // when
        CategoryDTO actual = categoryService.addCategory(category.toDTO());

        // then
        assertThat(actual).isEqualTo(category.toDTO());
    }

    @DisplayName("id로 카테고리 찾기")
    @Test
    void getProduct() {
        // given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));

        // when
        CategoryDTO actual = categoryService.getCategory(1L);

        // then
        assertThat(actual).isEqualTo(category.toDTO());
    }

    @DisplayName("카테고리 수정")
    @Test
    void updateProduct() {
        // given
        Category updatedCategory = new Category(1L, "updatedTest", "#000000", "updatedTestImageUrl",
            "updatedTest");
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        given(categoryRepository.save(any(Category.class))).willReturn(updatedCategory);

        // when
        CategoryDTO actual = categoryService.updateCategory(1L, updatedCategory.toDTO());

        // then
        assertThat(actual).isEqualTo(updatedCategory.toDTO());
    }

    @DisplayName("카테고리 삭제")
    @Test
    void deleteProduct() {
        // given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));

        // when
        categoryService.deleteCategory(1L);

        // then
        then(categoryRepository).should().delete(any(Category.class));
    }
}
