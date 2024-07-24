package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.CategoryRequest;
import gift.domain.entity.Category;
import gift.domain.exception.conflict.CategoryAlreadyExistsException;
import gift.domain.exception.notFound.CategoryNotFoundException;
import gift.domain.repository.CategoryRepository;
import gift.utilForTest.MockObjectSupplier;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void beforeEach() {
        this.category = MockObjectSupplier.get(Category.class);
    }

    @Test
    @DisplayName("[UnitTest] 카테고리 얻기")
    void findById() {
        given(categoryRepository.findById(eq(category.getId())))
            .willReturn(Optional.empty())
            .willReturn(Optional.of(category));

        // when & then (1)
        assertThatThrownBy(() -> categoryService.findById(category.getId()))
            .isInstanceOf(CategoryNotFoundException.class);

        // when (2)
        Category byId = categoryService.findById(category.getId());

        // then (2)
        assertThat(byId).isEqualTo(category);
        then(categoryRepository).should(times(2)).findById(eq(category.getId()));
    }

    @Test
    @DisplayName("[UnitTest] 카테고리 목록 얻기")
    void getCategories() {
        categoryService.getCategories();
        then(categoryRepository).should(times(1)).findAll();
    }

    @Test
    @DisplayName("[UnitTest] 카테고리 추가")
    void addCategory() {
        given(categoryRepository.findByName(category.getName())).willReturn(Optional.empty());
        given(categoryRepository.save(any())).willReturn(category);
        categoryService.addCategory(CategoryRequest.of(category));
        then(categoryRepository).should(times(1)).findByName(eq(category.getName()));
        then(categoryRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 카테고리 추가: 이미 존재하는 카테고리인 경우")
    void addCategory_duplicate() {
        given(categoryRepository.findByName(category.getName())).willReturn(Optional.of(category));
        assertThatThrownBy(() -> categoryService.addCategory(CategoryRequest.of(category)))
            .isInstanceOf(CategoryAlreadyExistsException.class);
        then(categoryRepository).should(times(1)).findByName(eq(category.getName()));
        then(categoryRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("[UnitTest] 카테고리 업데이트 - 이름이 같은 경우")
    void updateCategory() {
        Category category = CategoryRequest.of(this.category).toEntity();
        ReflectionTestUtils.setField(category, "id", 1L);
        CategoryRequest request = new CategoryRequest(category.getName(), "#002020", "image.jpg", "");
        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        categoryService.updateCategory(category.getId(), request);

        then(categoryRepository).should(times(1)).findById(any());
        then(categoryRepository).should(never()).findByName(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 카테고리 업데이트 - 이름 변경했는데 동일 이름이 이미 있는 경우")
    void updateCategory_duplicateName() {
        Category category = CategoryRequest.of(this.category).toEntity();
        ReflectionTestUtils.setField(category, "id", 1L);
        CategoryRequest request = new CategoryRequest("newName", "#002020", "image.jpg", "");
        given(categoryRepository.findById(any())).willReturn(Optional.of(category));
        given(categoryRepository.findByName(any())).willReturn(Optional.of(new Category("newName", "#000000", "", "")));

        assertThatThrownBy(() -> categoryService.updateCategory(category.getId(), request))
            .isInstanceOf(CategoryAlreadyExistsException.class);

        then(categoryRepository).should(times(1)).findById(any());
        then(categoryRepository).should(times(1)).findByName(any());
    }
}