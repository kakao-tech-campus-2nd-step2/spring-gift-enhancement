package gift.service;

import gift.dto.request.AddCategoryRequest;
import gift.dto.request.UpdateCategoryRequest;
import gift.dto.response.CategoryIdResponse;
import gift.dto.response.CategoryResponse;
import gift.entity.Category;
import gift.exception.CategoryNameDuplicateException;
import gift.exception.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("카테고리 서비스 단위테스트")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("모든 카테고리 조회")
    void getAllCategories() {
        //Given
        List<Category> categoryList = List.of(
                new Category(1L, "상품권", "test", "test", "test"),
                new Category(2L, "교환권", "test", "test", "test"),
                new Category(3L, "패션잡화", "test", "test", "test")
        );
        when(categoryRepository.findAll()).thenReturn(categoryList);

        //When
        List<CategoryResponse> categoryResponses = categoryService.getAllCategoryResponses();

        //Then
        assertThat(categoryResponses)
                .hasSize(3)
                .extracting("name")
                .containsExactly("상품권", "교환권", "패션잡화");
    }

    @Nested
    @DisplayName("카테고리 id로 조회")
    class Get {
        @Test
        @DisplayName("성공")
        void success() {
            //Given
            Category wantCategory = new Category(1L, "원하는 카테고리", "test", "test", "test");
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(wantCategory));

            //When
            Category result = categoryService.getCategory(1L);

            //Then
            assertThat(result).isNotNull()
                    .extracting("id", "name")
                    .containsExactly(1L, "원하는 카테고리");
        }

        @Test
        @DisplayName("실패 - 해당 카테고리 존재 안함")
        void fail() {
            //Given
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            //When Then
            assertThatThrownBy(() -> categoryService.getCategory(1L))
                    .isInstanceOf(CategoryNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("카테고리 추가")
    class Add {
        @Test
        @DisplayName("성공")
        void success() {
            //Given
            when(categoryRepository.existsByName(any())).thenReturn(false);
            AddCategoryRequest request = new AddCategoryRequest("상품권", "색", "이미지주소", "설명");
            when(categoryRepository.save(any())).thenReturn(new Category(1L, "상품권", "색", "이미지주소", "설명"));

            //When
            CategoryIdResponse response = categoryService.addCategory(request);

            //Then
            assertThat(response.id()).isEqualTo(1L);
        }

        @Test
        @DisplayName("실패 - 카테고리 이름 중복")
        void fail() {
            //Given
            when(categoryRepository.existsByName(any())).thenReturn(true);
            AddCategoryRequest request = new AddCategoryRequest("상품권", "색", "이미지주소", "설명");

            //When Then
            assertThatThrownBy(() -> categoryService.addCategory(request))
                    .isInstanceOf(CategoryNameDuplicateException.class);
        }
    }

    @Nested
    @DisplayName("카테고리 수정")
    class Update {
        @Test
        @DisplayName("성공")
        void success() {
            //Given
            Category existingCategory = new Category(1L, "기존", "기존", "기존", "기존");

            UpdateCategoryRequest request = new UpdateCategoryRequest(1L, "새로움", "새로움", "뉴이미지", "설명");
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));

            //When
            categoryService.updateCategory(request);

            //Then
            assertThat(existingCategory.getName()).isEqualTo("새로움");
        }

        @Test
        @DisplayName("실패 - 해당 카테고리 존재 안함")
        void fail() {
            //Given
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            UpdateCategoryRequest request = new UpdateCategoryRequest(1L, "새로움", "새로움", "뉴이미지", "설명");

            //When Then
            assertThatThrownBy(() -> categoryService.updateCategory(request))
                    .isInstanceOf(CategoryNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("카테고리 삭제")
    class Delete {
        @Test
        @DisplayName("성공")
        void success() {
            //Given
            Category deleteTargetCategory = new Category(1L, "타겟", "타겟", "타겟", "타겟");
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(deleteTargetCategory));

            //When
            categoryService.deleteCategory(1L);

            //Then
            verify(categoryRepository).delete(deleteTargetCategory);
        }

        @Test
        @DisplayName("실패 - 해당 카테고리 존재 안함")
        void fail() {
            //Given
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            //When Then
            assertThatThrownBy(() -> categoryService.deleteCategory(1L))
                    .isInstanceOf(CategoryNotFoundException.class);
        }
    }
}
