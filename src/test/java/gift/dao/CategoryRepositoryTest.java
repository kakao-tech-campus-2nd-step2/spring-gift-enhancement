package gift.dao;

import gift.product.dao.CategoryRepository;
import gift.product.dto.CategoryRequest;
import gift.product.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Category category = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();

        Category savedCategory = categoryRepository.save(category);
        Category foundCategory = categoryRepository.findById(savedCategory.getId())
                .orElse(null);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(savedCategory.getId());
    }

    @Test
    @DisplayName("카테고리 ID 조회 실패 테스트")
    void findByIdFailed() {
        Category category = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        categoryRepository.save(category);

        Category foundCategory = categoryRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundCategory).isNull();
    }

    @Test
    @DisplayName("카테고리 이름 조회 테스트")
    void findByName() {
        String name = "상품권";
        Category category = new Category.CategoryBuilder()
                .setName(name)
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        categoryRepository.save(category);

        Category foundCategory = categoryRepository.findByName(name)
                .orElse(null);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("카테고리 이름 조회 실패 테스트")
    void findByNameFailed() {
        String name = "교환권";
        Category category = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        categoryRepository.save(category);

        Category foundCategory = categoryRepository.findByName(name)
                .orElse(null);

        assertThat(foundCategory).isNull();
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() {
        Category category = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        Category savedCategory = categoryRepository.save(category);
        savedCategory.update(
                new CategoryRequest(
                        "교환권",
                        category.getColor(),
                        category.getImageUrl(),
                        category.getDescription()
        ));

        Category updatedCategory = categoryRepository.save(savedCategory);

        Category foundCategory = categoryRepository.findById(updatedCategory.getId())
                .orElse(null);
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo(updatedCategory.getName());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMember() {
        Category category = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getId());

        boolean exists = categoryRepository.existsById(savedCategory.getId());
        assertThat(exists).isFalse();
    }

}