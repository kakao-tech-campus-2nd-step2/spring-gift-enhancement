package gift.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import gift.model.product.Category;
import gift.repository.product.CategoryRepository;
import gift.service.product.CategoryService;
import gift.service.product.dto.CategoryCommand;
import gift.service.product.dto.CategoryModel;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 생성 테스트")
    void 카테고리_생성() {
        // given
        CategoryCommand.Register command = new CategoryCommand.Register("카테고리");
        given(categoryRepository.save(any(Category.class))).willReturn(new Category("카테고리"));

        // when
        final CategoryModel.Info result = categoryService.createCategory(command);
        // then
        assert result.name().equals("카테고리");
    }


    @Test
    @DisplayName("카테고리 생성 - 중복된 이름으로 인한 실패 테스트")
    void 카테고리_생성_중복_실패() {
        // given
        CategoryCommand.Register command = new CategoryCommand.Register("카테고리");
        given(categoryRepository.findByName(command.name())).willReturn(
            Optional.of(new Category("카테고리")));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(command);
        });
    }

}
