package gift.service;

import gift.dto.ProductCategoryRequest;
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
class ProductCategoryServiceTest {

    private final Pageable pageable = PageRequest.of(0, 10);
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    @DisplayName("정상 카테고리 추가하기")
    void addProductCategorySuccess() {
        //given
        var productCategoryRequest = new ProductCategoryRequest("상품카테고리", "상품설명", "#111111", "이미지");
        //when
        var savedProductCategory = productCategoryService.addCategory(productCategoryRequest);
        //then
        Assertions.assertThat(savedProductCategory.name()).isEqualTo("상품카테고리");

        productCategoryService.deleteCategory(savedProductCategory.id());
    }
}
