package gift.service;

import gift.dto.CategoryRequest;
import gift.exception.DuplicatedNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    private final Pageable pageable = PageRequest.of(0, 10);
    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("정상 카테고리 추가하기")
    void addProductCategorySuccess() {
        //given
        var productCategoryRequest = new CategoryRequest("상품카테고리", "상품설명", "#111111", "이미지");
        //when
        var savedProductCategory = categoryService.addCategory(productCategoryRequest);
        //then
        Assertions.assertThat(savedProductCategory.name()).isEqualTo("상품카테고리");

        categoryService.deleteCategory(savedProductCategory.id());
    }

    @Test
    @DisplayName("중복된 이름의 카테고리 추가하기")
    void addProductCategoryFailWithDuplicatedCategoryName() {
        //given
        var productCategoryRequest = new CategoryRequest("상품카테고리", "상품설명", "#111111", "이미지");
        var productCategory = categoryService.addCategory(productCategoryRequest);
        //when, then
        Assertions.assertThatThrownBy(() -> categoryService.addCategory(productCategoryRequest)).isInstanceOf(DuplicatedNameException.class);

        categoryService.deleteCategory(productCategory.id());
    }

    @Test
    @DisplayName("단일 카테고리 조회하기")
    void findProductCategory() {
        //given
        var productCategoryId = 1L;
        //when
        var productCategory = categoryService.getCategory(productCategoryId);
        //then
        Assertions.assertThat(productCategory.name()).isEqualTo("디지털/가전");
    }

    @Test
    @DisplayName("전체 카테고리 조회하기")
    void findProductCategories() {
        //given, when
        var productCategories = categoryService.getCategories(pageable);
        //then
        Assertions.assertThat(productCategories.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("카테고리 정보 변경하기")
    void updateProductCategory() {
        //given
        var productCategoryRequest = new CategoryRequest("상품카테고리", "상품설명", "#111111", "이미지");
        var savedProductCategory = categoryService.addCategory(productCategoryRequest);
        var updateRequest = new CategoryRequest("상품카테고리-수정", "상품설명-수정", "#111111", "이미지-수정");
        //when
        categoryService.updateCategory(savedProductCategory.id(), updateRequest);
        //then
        var updatedCategory = categoryService.getCategory(savedProductCategory.id());
        Assertions.assertThat(updatedCategory.name()).isEqualTo("상품카테고리-수정");

        categoryService.deleteCategory(savedProductCategory.id());
    }
}
