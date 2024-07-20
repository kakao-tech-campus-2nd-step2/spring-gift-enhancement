package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryDTO;
import gift.administrator.category.CategoryRepository;
import gift.administrator.category.CategoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);

    @BeforeEach
    void beforeEach() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    void getAllCategories() {
        //given
        Category category = new Category(1L, "상품권", "#ff11ff", "image.jpg", "");
        Category category1 = new Category(2L, "인형", "#dd11ff", "image.jpg", "none");
        given(categoryRepository.findAll()).willReturn(
            Arrays.asList(category, category1));
        CategoryDTO expected = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        CategoryDTO expected1 = new CategoryDTO(2L, "인형", "#dd11ff", "image.jpg", "none");

        //when
        List<CategoryDTO> actual = categoryService.getAllCategories();

        //then
        assertThat(actual).hasSize(2);
        assertThat(actual)
            .extracting(CategoryDTO::getName, CategoryDTO::getColor, CategoryDTO::getImageUrl, CategoryDTO::getDescription)
            .containsExactly(
                tuple(expected.getName(), expected.getColor(), expected.getImageUrl(), expected.getDescription()),
                tuple(expected1.getName(), expected1.getColor(), expected1.getImageUrl(), expected1.getDescription())
            );
    }

    @Test
    @DisplayName("아이디로 카테고리 검색")
    void getCategoryById() throws NotFoundException {
        //given
        CategoryDTO expected = new CategoryDTO(2L, "인형", "#dd11ff", "image.jpg", "none");
        given(categoryRepository.findById(any())).willReturn(
            Optional.of(new Category(2L, "인형", "#dd11ff", "image.jpg", "none")));

        //when
        CategoryDTO actual = categoryService.getCategoryById(2L);

        //then
        assertThat(actual)
            .extracting(CategoryDTO::getName, CategoryDTO::getColor, CategoryDTO::getImageUrl,
                CategoryDTO::getDescription)
            .containsExactly(expected.getName(), expected.getColor(), expected.getImageUrl(),
                expected.getDescription());
    }

    @Test
    @DisplayName("아이디로 카테고리 검색시 없는 아이디일 때 오류")
    void getCategoryByIdNotFoundException() throws NotFoundException {
        //given
        given(categoryRepository.findById(any())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> categoryService.getCategoryById(2L)).isInstanceOf(
            NotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 추가 시 이름 존재하면 오류")
    void addCategorySameNameError() {
        //given
        given(categoryRepository.existsByName(any())).willReturn(true);
        CategoryDTO categoryDTO = new CategoryDTO("이름", "색상", "이미지링크", "설명");

        //when

        //then
        assertThatIllegalArgumentException().isThrownBy(
            () -> categoryService.addCategory(categoryDTO)).withMessage("존재하는 이름입니다.");
    }

    @Test
    @DisplayName("카테고리 추가")
    void addCategory() {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "이름", "색상", "이미지링크", "설명");
        given(categoryRepository.existsByName(any())).willReturn(false);
        given(categoryRepository.save(any())).willReturn(categoryDTO.toCategory());

        //when
        categoryService.addCategory(categoryDTO);

        //then
        then(categoryRepository).should().save(any());
    }

    @Test
    @DisplayName("카테고리 업데이트시 없는 아이디일 때 오류")
    void updateCategoryNotFoundException() throws NotFoundException {
        //given
        given(categoryRepository.findById(any())).willReturn(Optional.empty());
        CategoryDTO categoryDTO = new CategoryDTO("상품권", "#11ff11", null, null);

        //when

        //then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryDTO)).isInstanceOf(
            NotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 업데이트 존재하는 이름일 때 오류")
    void updateCategoryExistingName() {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", null, null);
        Category category = new Category(2L, "상품권", "#ff11ff", null, null);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(categoryDTO.toCategory()));
        given(categoryRepository.existsByNameAndIdNot(categoryDTO.getName(),
            categoryDTO.getId())).willReturn(true);

        //when

        //then
        assertThatIllegalArgumentException().isThrownBy(
            () -> categoryService.updateCategory(categoryDTO)).withMessage("존재하는 이름입니다.");
    }

    @Test
    @DisplayName("카테고리 업데이트")
    void updateCategory() throws NotFoundException {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", null, null);
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "인형", "#ff11ff", "image.url", "dolls");
        given(categoryRepository.findById(1L)).willReturn(Optional.of(categoryDTO.toCategory()));
        given(categoryRepository.existsByName(any())).willReturn(false);
        given(categoryRepository.save(any())).willReturn(categoryDTO.toCategory());

        //when
        categoryService.updateCategory(categoryDTO1);

        //then
        then(categoryRepository).should().save(any());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() throws NotFoundException {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", null, null);
        given(categoryRepository.findById(1L)).willReturn(
            Optional.ofNullable(categoryDTO.toCategory()));

        //when
        categoryService.deleteCategory(1L);

        //then
        then(categoryRepository).should().deleteById(1L);
    }

    @Test
    @DisplayName("카테고리 삭제시 아이디가 없는 오류")
    void deleteCategoryNotFoundException() throws NotFoundException {
        //given
        given(categoryRepository.findById(any())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> categoryService.deleteCategory(1L)).isInstanceOf(
            NotFoundException.class);
    }
}
