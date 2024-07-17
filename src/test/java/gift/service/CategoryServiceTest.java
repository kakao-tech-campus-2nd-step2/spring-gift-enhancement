package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.category.dto.CategoryRequest;
import gift.domain.category.dto.CategoryResponse;
import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import gift.domain.category.service.CategoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    void getAllCategoriesTest(){
        List<Category> categoryList = Arrays.asList(new Category(1L,"name1", "color", "imageUrl", "description"),
            new Category(2L,"name1", "color", "imageUrl", "description"));

        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categoryPage =new PageImpl<>(categoryList,pageable, categoryList.size());

        doReturn(categoryPage).when(categoryRepository).findAll(pageable);

        Page<CategoryResponse> expected = categoryPage.map(this::entityToDto);

        // when
        Page<CategoryResponse> actual = categoryService.getAllCategories(pageNo, pageSize);

        // then
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> IntStream.range(0, actual.getContent().size()).forEach(i -> {
                assertThat(actual.getContent().get(i).getName())
                    .isEqualTo(expected.getContent().get(i).getName());
                assertThat(actual.getContent().get(i).getColor())
                    .isEqualTo(expected.getContent().get(i).getColor());
                assertThat(actual.getContent().get(i).getImageUrl())
                    .isEqualTo(expected.getContent().get(i).getImageUrl());
                assertThat(actual.getContent().get(i).getDescription())
                    .isEqualTo(expected.getContent().get(i).getDescription());
            })
        );
    }

    @Test
    @DisplayName("카테고리 생성 테스트")
    void createCategoryTest(){
        // given
        CategoryRequest request = new CategoryRequest("name", "color", "imageUrl", "description");

        Category savedCategory = new Category(1L, request.getName(), request.getColor(), request.getImageUrl(), request.getDescription());

        doReturn(savedCategory).when(categoryRepository).save(any());

        CategoryResponse expected = entityToDto(savedCategory);

        // when
        CategoryResponse actual = categoryService.createCategory(request);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategoryTest(){
        // given
        Long id = 1L;
        CategoryRequest request = new CategoryRequest("update", "color", "imageUrl", "description");
        Category savedCategory = spy(new Category(1L, "name", "color", "imageUrl", "description"));

        doReturn(Optional.of(savedCategory)).when(categoryRepository).findById(any());
        doNothing().when(savedCategory).updateAll(request.getName(), request.getColor(), request.getImageUrl(), request.getDescription());

        Category updateCategory = new Category(1L, request.getName(), request.getColor(), request.getImageUrl(), request.getImageUrl());
        doReturn(updateCategory).when(categoryRepository).save(any());

        CategoryResponse expected = entityToDto(updateCategory);

        // when
        CategoryResponse actual = categoryService.updateCategory(id, request);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription())
        );
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategoryTest(){
        Long id = 1L;
        Category savedCategory = new Category(1L, "name", "color", "imageUrl", "description");

        doReturn(Optional.of(savedCategory)).when(categoryRepository).findById(id);

        // when
        categoryService.deleteCategory(id);

        // then
        verify(categoryRepository, times(1)).delete(savedCategory);
    }

    private CategoryResponse entityToDto(Category category){
        return new CategoryResponse(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
    }
}