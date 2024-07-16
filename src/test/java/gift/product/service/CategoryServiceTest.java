package gift.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import gift.product.domain.Category;
import gift.product.persistence.CategoryRepository;
import gift.product.service.dto.CategoryParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Category 생성 테스트[성공]")
    void createCategoryTest() {
        // given
        CategoryParam categoryParam = new CategoryParam("카테고리", "색상", "이미지 URL", "설명");
        Category savedCategory = new Category(1L, "카테고리", "색상", "이미지 URL", "설명");

        // when
        when(categoryRepository.save(any())).thenReturn(savedCategory);
        Long savedId = categoryService.createCategory(categoryParam);

        // then
        assertThat(savedId).isEqualTo(1L);
        then(categoryRepository).should().save(any());
    }
}