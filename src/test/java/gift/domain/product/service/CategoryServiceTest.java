package gift.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.domain.product.dto.CategoryResponse;
import gift.domain.product.repository.CategoryJpaRepository;
import gift.domain.product.entity.Category;
import gift.exception.InvalidCategoryInfoException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    @DisplayName("카테고리 전체 조회 서비스 테스트")
    void readAll() {
        // given
        List<Category> expected = List.of(
            new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test"),
            new Category(2L, "상품권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test"),
            new Category(3L, "뷰티", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test"),
            new Category(4L, "패션", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test")
        );
        given(categoryJpaRepository.findAll()).willReturn(expected);

        // when
        List<CategoryResponse> actual = categoryService.readAll();

        // then
        then(categoryJpaRepository).should().findAll();
        assertThat(actual).isEqualTo(expected.stream().map(CategoryResponse::from).toList());
    }

    @Test
    @DisplayName("카테고리 ID로 조회 서비스 테스트")
    void readById_success() {
        // given
        Category expected = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        given(categoryJpaRepository.findById(anyLong())).willReturn(Optional.of(expected));

        // when
        Category actual = categoryService.readById(1L);

        // then
        then(categoryJpaRepository).should().findById(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("카테고리 ID로 조회 실패 테스트")
    void readById_fail() {
        // given
        given(categoryJpaRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> categoryService.readById(1L))
            .isInstanceOf(InvalidCategoryInfoException.class);
    }
}