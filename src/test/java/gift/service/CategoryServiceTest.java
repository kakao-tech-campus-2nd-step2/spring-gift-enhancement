package gift.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import gift.exception.category.DuplicateCategoryException;
import gift.exception.category.NotFoundCategoryException;
import gift.model.Category;
import gift.repository.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @DisplayName("카테고리 생성 테스트")
    @Test
    void save() {
        //given
        String name = "카테고리";
        given(categoryRepository.save(any(Category.class)))
            .will(invocation -> {
                Category category = invocation.getArgument(0, Category.class);
                return new Category(1L, category.getName());
            });
        //when
        Category createdCategory = categoryService.addCategory(name);
        //then
        assertThat(createdCategory.getId()).isEqualTo(1L);
        assertThat(createdCategory.getName()).isEqualTo(name);
    }

    @DisplayName("카테고리 중복 생성 테스트")
    @Test
    void failSave() {
        //given
        String name = "이미 존재하는 카테고리";
        given(categoryRepository.save(any(Category.class)))
            .will(invocation -> {
                Category category = invocation.getArgument(0, Category.class);
                return new Category(1L, category.getName());
            });
        given(categoryRepository.findByName(name))
            .willThrow(DuplicateCategoryException.class);
        //when then
        assertThatThrownBy(() -> categoryService.addCategory(name))
            .isInstanceOf(DuplicateCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    void findAll() {
        // given
        given(categoryRepository.findAll())
            .will(invocationOnMock -> Collections.emptyList());
        // when
        categoryService.getAllCategories();
        // then
        then(categoryRepository).should().findAll();
    }

    @Test
    @DisplayName("카테고리 단건 조회 테스트")
    void findById() {
        //given
        String name = "카테고리";
        given(categoryRepository.findById(any(Long.class)))
            .will(invocationOnMock -> {
                Long id = invocationOnMock.getArgument(0, Long.class);
                return Optional.of(new Category(id, name));
            });
        //when
        Category findCategory = categoryService.getCategory(1L);
        //then
        then(categoryRepository).should().findById(1L);
        assertThat(findCategory.getId()).isEqualTo(1L);
        assertThat(findCategory.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("카테고리 단건 조회 실패 테스트")
    void failFindById() {
        //given
        String name = "존재하지 않는 카테고리";
        given(categoryRepository.findById(any(Long.class)))
            .willThrow(NotFoundCategoryException.class);
        //when
        assertThatThrownBy(() -> categoryService.getCategory(1L))
            .isInstanceOf(NotFoundCategoryException.class);
        //then
    }

    @Test
    @DisplayName("카테고리 변경 테스트")
    void update() {
        //given
        String oldName = "카테고리";
        Category savedCategory = mock(Category.class);
        String newName = "변경된 카테고리";
        given(categoryRepository.findById(any(Long.class)))
            .willReturn(Optional.of(savedCategory));
        //when
        categoryService.updateCategory(1L, newName);
        //then
        then(categoryRepository).should().findById(1L);
        then(savedCategory).should().updateCategory(newName);
    }

    @Test
    @DisplayName("카테고리 변경 실패 테스트")
    void failUpdate() {
        //given
        String newName = "변경된 카테고리";
        given(categoryRepository.findById(any(Long.class)))
            .willReturn(Optional.empty());
        //when //then
        assertThatThrownBy(() -> categoryService.updateCategory(1L, newName))
            .isInstanceOf(NotFoundCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void delete() {
        //given
        Category savedCategory = mock(Category.class);
        given(categoryRepository.findById(any(Long.class)))
            .willReturn(Optional.of(savedCategory));
        //when
        categoryService.removeCategory(1L);
        //then
        then(categoryRepository).should().findById(1L);
        then(categoryRepository).should().delete(savedCategory);
    }




}