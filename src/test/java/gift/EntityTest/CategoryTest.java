package gift.EntityTest;

import gift.Model.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryTest {

    @Test
    void creationTest(){
        Category category = new Category("식품", "#812f3D", "식품 url", "식품 description");
        assertAll(
                ()->assertThat(category.getName()).isEqualTo("식품"),
                ()->assertThat(category.getColor()).isEqualTo("#812f3D"),
                ()->assertThat(category.getImageUrl()).isEqualTo("식품 url"),
                ()->assertThat(category.getDescription()).isEqualTo("식품 description")
        );
    }

    @Test
    void updateTest(){
        Category category = new Category("식품", "#812f3D", "식품 url", "식품 description");
        category.update("음료", "#732d2b", "음료 url", "음료 description");

        assertAll(
                () -> assertThat(category.getName()).isEqualTo("음료"),
                () -> assertThat(category.getColor()).isEqualTo("#732d2b"),
                () -> assertThat(category.getImageUrl()).isEqualTo("음료 url"),
                () -> assertThat(category.getDescription()).isEqualTo("음료 description")
        );
    }


}
