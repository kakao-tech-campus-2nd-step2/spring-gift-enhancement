package gift.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import gift.dto.category.CategoryResponse;
import gift.dto.category.CreateCategoryRequest;
import gift.dto.category.UpdateCategoryRequest;
import gift.entity.Category;
import gift.exception.category.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import gift.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceTest implements AutoCloseable {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private AutoCloseable closeable;

    @Override
    public void close() throws Exception {
        closeable.close();
    }

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("get all categories test")
    void getAllCategoriesTest() {
        // given
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        List<Category> categoryList = List.of(category1, category2, category3);
        given(categoryRepository.findAll()).willReturn(categoryList);

        // when
        List<CategoryResponse> categories = categoryService.getAllCategories();

        // then
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(3);
        then(categoryRepository).should(times(1)).findAll();
    }

    @Test
    @DisplayName("get all categories empty test")
    void getAllCategoriesEmptyTest() {
        // given
        given(categoryRepository.findAll()).willReturn(List.of());

        // when
        List<CategoryResponse> categories = categoryService.getAllCategories();

        // then
        assertThat(categories).isEmpty();
        then(categoryRepository).should(times(1)).findAll();
    }

    @Test
    @DisplayName("get category by id test")
    void getCategoryByIdTest() {
        // given
        Category category = new Category(1L, "Category 1");
        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        // when
        CategoryResponse actual = categoryService.getCategory(1L);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(1L);
        assertThat(actual.name()).isEqualTo("Category 1");
        then(categoryRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("get category by not exist id test")
    void getCategoryByNotExistIdTest() {
        // given
        given(categoryRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> categoryService.getCategory(999L)).isInstanceOf(
            CategoryNotFoundException.class);
        then(categoryRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("create category test")
    void createCategoryTest() {
        // given
        CreateCategoryRequest request = new CreateCategoryRequest("Category 1");
        given(categoryRepository.existsByName(any())).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willReturn(
            new Category(1L, "Category 1"));

        Long expectedId = 1L;

        // when
        var actual = categoryService.createCategory(request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expectedId);
        then(categoryRepository).should(times(1)).save(any(Category.class));
        then(categoryRepository).should(times(1)).existsByName(any());
    }

    @Test
    @DisplayName("update category test")
    void updateCategoryTest() {
        // given
        String newName = "update category";
        UpdateCategoryRequest request = new UpdateCategoryRequest(newName);

        Category existingCategory = new Category(1L, "Category 1");
        given(categoryRepository.findById(any())).willReturn(Optional.of(existingCategory));

        // when
        categoryService.updateCategory(1L, request);

        // then
        then(categoryRepository).should(times(1)).findById(any());
        assertThat(existingCategory.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("update not exist category test")
    void updateNotExistCategoryTest() {
        // given
        String newName = "update category";
        UpdateCategoryRequest request = new UpdateCategoryRequest(newName);
        given(categoryRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> categoryService.updateCategory(1L, request))
            .isInstanceOf(CategoryNotFoundException.class);
        then(categoryRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("delete category test")
    void deleteCategoryTest() {
        // given
        given(categoryRepository.existsById(any())).willReturn(true);
        willDoNothing().given(categoryRepository).deleteById(any(Long.class));

        // when
        categoryService.deleteCategory(1L);

        // then
        then(categoryRepository).should(times(1)).existsById(any());
        then(categoryRepository).should(times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("delete not exist category test")
    void deleteNotExistCategoryTest() {
        // given
        given(categoryRepository.existsById(any())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
            .isInstanceOf(CategoryNotFoundException.class);
        then(categoryRepository).should(times(1)).existsById(any());
        then(categoryRepository).should(times(0)).deleteById(any());
    }
}
