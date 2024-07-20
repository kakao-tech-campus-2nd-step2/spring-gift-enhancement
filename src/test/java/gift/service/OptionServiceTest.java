package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.dto.OptionDto;
import gift.product.dto.OptionResponse;
import gift.product.dto.OptionSubtractAmount;
import gift.product.model.Category;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import gift.product.service.OptionService;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OptionServiceTest {
    @Autowired
    OptionService optionService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 옵션_추가() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        //when
        Option option = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //then
        assertThat(option.getId()).isNotNull();
    }

    @Test
    void 옵션_전체_조회() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        optionService.insertOption(new OptionDto("테스트옵션1", 1, product.getId()));
        optionService.insertOption(new OptionDto("테스트옵션2", 1, product.getId()));

        //when
        List<Option> options = optionService.getOptionAll();

        //then
        assertThat(options).hasSize(2);
    }

    @Test
    void 특정_상품의_옵션_전체_조회() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product1 = productRepository.save(new Product("테스트상품1", 1500, "테스트주소", category));
        Product product2 = productRepository.save(new Product("테스트상품2", 1500, "테스트주소", category));

        optionService.insertOption(new OptionDto("테스트옵션1", 1, product1.getId()));
        optionService.insertOption(new OptionDto("테스트옵션2", 1, product2.getId()));

        //when
        List<OptionResponse> optionResponses = optionService.getOptionAllByProductId(product1.getId());

        //then
        assertThat(optionResponses).hasSize(1);
    }

    @Test
    void 옵션_조회() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //when
        Option option = optionService.getOption(insertedOption.getId());

        //then
        assertThat(option.getId()).isEqualTo(insertedOption.getId());
    }

    @Test
    void 옵션_수정() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //when
        OptionDto updatedOptionDto = new OptionDto("테스트옵션_수정됨", 2, product.getId());
        Option updatedOption = optionService.updateOption(insertedOption.getId(), updatedOptionDto);

        //then
        assertSoftly(softly -> {
            assertThat(updatedOption.getName()).isEqualTo(updatedOptionDto.name());
            assertThat(updatedOption.getQuantity()).isEqualTo(updatedOptionDto.quentity());
        });
    }

    @Test
    void 옵션_삭제() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        optionService.insertOption(new OptionDto("테스트옵션1", 1, product.getId()));
        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션2", 1, product.getId()));

        //when
        optionService.deleteOption(insertedOption.getId());

        //then
        assertThatThrownBy(() -> optionService.getOption(insertedOption.getId())).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 옵션_수량_차감() {
        //given
        int QUANTITY = 10;
        int SUBTRACT_AMOUNT = 3;

        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션", QUANTITY, product.getId()));

        //when
        Option subtractedOption = optionService.subtractOption(insertedOption.getId(), new OptionSubtractAmount(SUBTRACT_AMOUNT));

        //then
        assertThat(subtractedOption.getQuantity()).isEqualTo(QUANTITY - SUBTRACT_AMOUNT);
    }

    @Test
    void 존재하지_않는_상품에_대한_옵션_추가() {
        assertThatThrownBy(
            () -> optionService.insertOption(new OptionDto("테스트옵션", 1, -1L))).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 옵션의_대상을_존재하지_않는_상품으로_수정() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //when, then
        assertThatThrownBy(
            () -> optionService.updateOption(insertedOption.getId(), new OptionDto("테스트옵션", 1, -1L))).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 존재하지_않는_옵션_조회() {
        assertThatThrownBy(
            () -> optionService.getOption(-1L)).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 이미_존재하는_옵션_중복_추가() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));
        optionService.insertOption(new OptionDto("테스트옵션중복명", 1, product.getId()));

        //when, then
        assertThatThrownBy(
            () -> optionService.insertOption(new OptionDto("테스트옵션중복명", 1, product.getId()))).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void 옵션_수량보다_더_많이_차감() {
        //given
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));

        Option insertedOption = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //when, then
        assertThatThrownBy(() -> optionService.subtractOption(insertedOption.getId(), new OptionSubtractAmount(999))).isInstanceOf(IllegalArgumentException.class);
    }
}
