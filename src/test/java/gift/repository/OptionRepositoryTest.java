package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import gift.model.categories.Category;
import gift.model.item.Item;
import gift.model.option.Option;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

public class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    private final String testName1 = "option1";
    private final String testName2 = "option2";
    private final String testName3 = "option3";
    private final Category testCategory = new Category("cate", "img");
    private final Item testItem = new Item("item", 2000L, "url", testCategory);

    @BeforeEach
    void setUp() {
        categoryRepository.save(testCategory);
        itemRepository.save(testItem);
    }


    @DisplayName("옵션 추가 성공 테스트")
    @Test
    void testInsertOption() {
        Option option = new Option(testName1, 1000L, testItem);
        Option result = optionRepository.save(option);
        assertThat(result).usingRecursiveComparison().isEqualTo(option);
    }

    @DisplayName("옵션 추가 실패 테스트")
    @Test
    void testInsertOptionFail() {
        Option option = new Option(testName1, 1000L, null);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
            () -> optionRepository.save(option));
    }

    @DisplayName("옵션 수정 성공 테스트")
    @Test
    void testUpdateOption() {
        Option option = new Option(testName1, 1000L, testItem);
        optionRepository.save(option);
        Option updated = new Option(option.getId(), testName2, 2000L, testItem);
        optionRepository.save(updated);
        Option result = optionRepository.findById(option.getId()).get();
        assertThat(result).usingRecursiveComparison().isEqualTo(updated);
    }

    @DisplayName("옵션 목록 조회 성공 테스트")
    @Test
    void testFindOptionListByItemId() {
        Option option1 = new Option(testName1, 1000L, testItem);
        Option option2 = new Option(testName2, 1000L, testItem);
        Option option3 = new Option(testName3, 1000L, testItem);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);
        List<Option> list = new ArrayList<>(List.of(option1, option2, option3));

        List<Option> result = optionRepository.findAllByItemId(testItem.getId());
        assertThat(result).usingRecursiveComparison().isEqualTo(list);
    }
}
