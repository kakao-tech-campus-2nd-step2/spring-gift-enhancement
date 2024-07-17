package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.product.dto.CategoryDto;
import gift.product.model.Category;
import gift.product.service.CategoryService;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    void 카테고리_추가() {
        //given
        CategoryDto categoryDto = new CategoryDto("테스트카테고리1");

        //when
        Category insertedCategory = categoryService.insertCategory(categoryDto);

        //then
        assertThat(insertedCategory.getId()).isNotNull();
    }

    @Test
    void 카테고리_조회() {
        //given
        CategoryDto categoryDto = new CategoryDto("테스트카테고리1");
        Category insertedCategory = categoryService.insertCategory(categoryDto);

        //when
        Category category = categoryService.getCategory(insertedCategory.getId());

        //then
        assertThat(category.getId()).isEqualTo(insertedCategory.getId());
    }

    @Test
    void 카테고리_전체_조회() {
        //given
        CategoryDto categoryDto1 = new CategoryDto("테스트카테고리1");
        CategoryDto categoryDto2 = new CategoryDto("테스트카테고리2");
        categoryService.insertCategory(categoryDto1);
        categoryService.insertCategory(categoryDto2);

        //when
        List<Category> categories = categoryService.getCategoryAll();

        //then
        assertThat(categories).hasSize(2);
    }

    @Test
    void 카테고리_수정() {
        //given
        CategoryDto categoryDto = new CategoryDto("테스트카테고리1");
        Category insertedCategory = categoryService.insertCategory(categoryDto);

        //when
        CategoryDto updatedCategoryDto = new CategoryDto("테스트카테고리2");
        Category updatedCategory = categoryService.updateCategory(insertedCategory.getId(),
            updatedCategoryDto);

        //then
        assertThat(updatedCategory.getName()).isEqualTo(updatedCategoryDto.name());
    }

    @Test
    void 카테고리_삭제() {
        //given
        CategoryDto categoryDto = new CategoryDto("테스트카테고리1");
        Category insertedCategory = categoryService.insertCategory(categoryDto);

        //when
        categoryService.deleteCategory(insertedCategory.getId());

        //then
        assertThatThrownBy(
            () -> categoryService.getCategory(insertedCategory.getId())).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 존재하지_않는_카테고리_조회() {
        assertThatThrownBy(() -> categoryService.getCategory(-1L)).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 이미_존재하는_카테고리_추가() {
        //given
        CategoryDto categoryDto = new CategoryDto("테스트카테고리1");
        categoryService.insertCategory(categoryDto);

        //when, then
        assertThatThrownBy(
            () -> categoryService.insertCategory(categoryDto)).isInstanceOf(
            IllegalArgumentException.class);
    }

}
