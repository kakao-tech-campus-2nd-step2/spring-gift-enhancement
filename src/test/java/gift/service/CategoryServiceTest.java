package gift.service;

import gift.controller.dto.request.CreateCategoryRequest;
import gift.controller.dto.request.UpdateCategoryRequest;
import gift.model.Category;
import gift.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 업데이트 테스트[성공]")
    void updateCategory() {
        // given
        String name = "카테고리";
        String color = "#123456";
        String imageUrl = "이미지url";
        String description = "설명";
        String description2 = "설명2";
        CreateCategoryRequest request = new CreateCategoryRequest(name, color, imageUrl, description);
        Long id = categoryService.save(request);
        UpdateCategoryRequest request2 = new UpdateCategoryRequest(id, name, color, imageUrl, description2);

        // when
        categoryService.updateById(request2);
        Category category = categoryRepository.findById(id).get();

        // then
        assertThat(category.getName()).isEqualTo(name);
        assertThat(category.getColor()).isEqualTo(color);
        assertThat(category.getImageUrl()).isEqualTo(imageUrl);
        assertThat(category.getDescription()).isEqualTo(description2);
    }

}