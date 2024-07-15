package gift.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import gift.dto.category.CategoryResponse;
import gift.entity.Category;
import gift.repository.CategoryRepository;
import gift.service.CategoryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        Pageable pageable = Pageable.unpaged();
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        List<Category> categoryList = List.of(category1, category2, category3);
        Page<Category> categoryPage = new PageImpl<>(categoryList);
        given(categoryRepository.findAll(pageable)).willReturn(categoryPage);

        // when
        List<CategoryResponse> categories = categoryService.getAllCategories(pageable).toList();

        // then
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(3);
        then(categoryRepository).should(times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("get all categories empty test")
    void getAllCategoriesEmptyTest() {
        // given
        Pageable pageable = Pageable.unpaged();
        given(categoryRepository.findAll(pageable)).willReturn(Page.empty());

        // when
        Page<CategoryResponse> categories = categoryService.getAllCategories(pageable);

        // then
        assertThat(categories).isEmpty();
        then(categoryRepository).should(times(1)).findAll(pageable);
    }
}
