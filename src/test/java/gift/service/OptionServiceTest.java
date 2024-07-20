package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import gift.product.dto.OptionDto;
import gift.product.dto.OptionResponse;
import gift.product.dto.OptionSubtractAmount;
import gift.product.model.Category;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import gift.product.service.OptionService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OptionServiceTest {
    @InjectMocks
    OptionService optionService;

    @Mock
    OptionRepository optionRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Test
    void 옵션_추가() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.existsByNameAndProductId("테스트옵션", product.getId())).willReturn(false);

        //when
        optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId()));

        //then
        then(optionRepository).should().save(any());
    }

    @Test
    void 옵션_전체_조회() {
        //when
        optionService.getOptionAll();

        //then
        then(optionRepository).should().findAll();
    }

    @Test
    void 특정_상품의_옵션_전체_조회() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        //when
        optionService.getOptionAllByProductId(1L);

        //then
        then(optionRepository).should().findAllByProductId(1L);
    }

    @Test
    void 옵션_조회() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        Option option = new Option(1L, "테스트옵션", 1, product);
        given(optionRepository.findById(1L)).willReturn(Optional.of(option));

        //when
        optionService.getOption(1L);

        //then
        then(optionRepository).should().findById(1L);
    }

    @Test
    void 옵션_수정() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        Option option = new Option(1L, "테스트옵션", 1, product);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.existsByNameAndProductId("테스트옵션", product.getId())).willReturn(false);
        given(optionRepository.save(any())).willReturn(option);
        Long insertedOptionId = optionService.insertOption(new OptionDto("테스트옵션", 1, product.getId())).getId();

        given(optionRepository.findById(insertedOptionId)).willReturn(
            Optional.of(option));
        clearInvocations(optionRepository);

        //when
        optionService.updateOption(insertedOptionId, new OptionDto("테스트옵션수정", 1, product.getId()));

        //then
        then(optionRepository).should().save(any());
    }

    @Test
    void 옵션_삭제() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        Option option2 = new Option(2L, "테스트옵션2", 1, product);
        given(optionRepository.findById(2L)).willReturn(Optional.of(option2));

        OptionResponse optionResponse1 = new OptionResponse(1L, "테스트옵션1", 1);
        OptionResponse optionResponse2 = new OptionResponse(2L, "테스트옵션2", 1);
        given(optionRepository.findAllByProductId(1L)).willReturn(List.of(optionResponse1, optionResponse2));

        //when
        optionService.deleteOption(option2.getId());

        //then
        then(optionRepository).should().deleteById(option2.getId());
    }

    @Test
    void 옵션_수량_차감() {
        //given
        int QUANTITY = 10;
        int SUBTRACT_AMOUNT = 3;

        Category category = new Category(1L, "테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        Option option = new Option(1L, "테스트옵션2", QUANTITY, product);
        given(optionRepository.findById(1L)).willReturn(Optional.of(option));

        //when
        optionService.subtractOption(1L, new OptionSubtractAmount(SUBTRACT_AMOUNT));

        //then
        then(optionRepository).should().save(any());
    }

    @Test
    void 존재하지_않는_상품에_대한_옵션_추가() {
        //given
        given(optionRepository.existsByNameAndProductId("테스트옵션", -1L)).willReturn(false);
        given(productRepository.findById(-1L)).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(
            () -> optionService.insertOption(new OptionDto("테스트옵션", 1, -1L))).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 옵션의_대상을_존재하지_않는_상품으로_수정() {
        //given
        given(productRepository.findById(1L)).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(
            () -> optionService.updateOption(1L, new OptionDto("테스트옵션수정", 1, 1L))).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 존재하지_않는_옵션_조회() {
        //given
        given(optionRepository.findById(any())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(
            () -> optionService.getOption(-1L)).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    void 이미_존재하는_옵션_중복_추가() {
        //given
        given(optionRepository.existsByNameAndProductId("테스트옵션중복명", 1L)).willReturn(true);

        //when, then
        assertThatThrownBy(
            () -> optionService.insertOption(new OptionDto("테스트옵션중복명", 1, 1L))).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void 옵션_수량보다_더_많이_차감() {
        //given
        Category category = new Category("테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        Option option = new Option(1L, "테스트옵션", 1, product);
        given(optionRepository.findById(1L)).willReturn(Optional.of(option));

        //when, then
        assertThatThrownBy(() -> optionService.subtractOption(1L, new OptionSubtractAmount(999))).isInstanceOf(IllegalArgumentException.class);
    }
}
