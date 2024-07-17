package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.dto.CategoryRequest;
import gift.main.entity.Category;
import gift.main.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class CategoryServiceTest {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final EntityManager entityManager;
    @Autowired
    CategoryServiceTest(CategoryRepository categoryRepository, CategoryService categoryService, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.entityManager = entityManager;
    }

    @Test
    public void updateCategoryTest() {
        //given
        Category category = categoryRepository.findByName("패션").get();
        CategoryRequest udateCategoryRequest = new CategoryRequest(1000, "의류");

        //when
        categoryService.updateCategory(category.getId(), udateCategoryRequest);

        //then
        assertThat(categoryRepository.findByUniNumber(1000).get()).isEqualTo(category);

    }

    @Test
    public void addDuplicateNameCategoryTest() {
        //given
        Category category = categoryRepository.findByName("패션").get();
        final String DUPLICATE_NAME = category.getName();
        CategoryRequest categoryRequest = new CategoryRequest(100, DUPLICATE_NAME);


        //when - then
        assertThatThrownBy(() -> categoryService.addCategory(categoryRequest))
                .isInstanceOf(CustomException.class);
    }

    @Test
    public void updateDuplicateUniNumberCategoryTest() {
        //given
        Category category = categoryRepository.findByName("패션").get();
        final int DUPLICATE_UNI_NUMER = category.getUniNumber();
        CategoryRequest categoryRequest = new CategoryRequest(DUPLICATE_UNI_NUMER, "의류");


        //when - then
        assertThatThrownBy(() -> categoryService.addCategory(categoryRequest))
                .isInstanceOf(CustomException.class);

    }
}