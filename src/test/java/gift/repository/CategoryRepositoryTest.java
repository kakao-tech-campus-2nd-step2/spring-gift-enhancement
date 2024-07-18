package gift.repository;

import static org.assertj.core.api.Assertions.as;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Category;
import gift.exception.DataNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void save() {
        //given
        Category expected = new Category("카테고리");

        //when
        Category actual = categoryRepository.save(expected);

        //then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo("카테고리")
        );
    }

    @Test
    void findById() {
        //given
        Category expected = new Category("카테고리");
        categoryRepository.save(expected);

        //when
        Category actual = categoryRepository.findById(expected.getId()).orElseThrow(
            () -> new DataNotFoundException("")
        );

        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );


    }

    @Test
    void findAll() {
        //given
        //data.sql에 삽입한 데이터로 조회
        //when
        List<Category> actual = categoryRepository.findAll();

        //then
        assertThat(actual.size()).isEqualTo(13);


    }
}