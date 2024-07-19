package gift.option;

import static gift.exception.ErrorMessage.OPTION_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.OPTION_NAME_ALLOWED_CHARACTER;
import static gift.exception.ErrorMessage.OPTION_NAME_LENGTH;
import static gift.exception.ErrorMessage.OPTION_NOT_FOUND;
import static gift.exception.ErrorMessage.OPTION_QUANTITY_SIZE;
import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.category.Category;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = OptionService.class)
public class OptionServiceTest {

    @MockBean
    private OptionRepository optionRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private OptionService optionService;

    // given values
    private long productId;
    private Product product;
    private OptionDTO optionDTO;
    private Option option;

    static class OptionQuantitySizeError implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(0),
                Arguments.of(100000000),
                Arguments.of(Integer.MAX_VALUE),
                Arguments.of(Integer.MIN_VALUE)
            );
        }
    }

    static class OptionNameLengthErrorMethodSource implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("thisSentenceIsTooLongUseForOptionNameDoesNotItHelloWorldHelloWorld"),
                Arguments.of("이문장은옵션의이름으로쓰기에는너무길어요안그런가요50자나되는문장을뭐라고써야할지도모르겠네요이제는딱맞는거같아요"),
                Arguments.of(
                    "공백 포함 공백 포함 공백 포함 공백 포함 [] 공백 포함 [ ] ( ) + - & / _ 공백 포함 공백 포함 공백 포함 ")
            );
        }
    }

    static class OptionNameAllowedCharacterMethodSource implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("한글과영어 그리고 특수문자 ()[]+-&/_ 😀"),
                Arguments.of("~!@#$%^&*()_+{}|\"'")
            );
        }
    }

    @BeforeEach
    void setUp() {
        productId = 1L;

        product = new Product(
            1L,
            "product",
            1,
            "imageUrl",
            new Category(1L, "category")
        );

        optionDTO = new OptionDTO(
            1L,
            "option-1",
            1
        );

        option = new Option(
            1L,
            "option-1",
            1,
            product
        );
    }

    @Nested
    @DisplayName("[Unit] get options test")
    class getOptionsTest {

        private List<Option> options;

        @BeforeEach
        void setUp() {
            //given
            options = List.of(
                new Option(1L, "option-1", 1, product),
                new Option(2L, "option-2", 2, product),
                new Option(3L, "option-3", 3, product)
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findAllByProductId(productId))
                .thenReturn(options);
        }

        @Test
        @DisplayName("success")
        void success() {
            //given
            List<OptionDTO> expected = List.of(
                new OptionDTO(1L, "option-1", 1),
                new OptionDTO(2L, "option-2", 2),
                new OptionDTO(3L, "option-3", 3)
            );

            //when
            List<OptionDTO> actual = optionService.getOptions(productId);

            //then
            assertEquals(actual, expected);
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //given
            options = List.of();

            //when
            when(productRepository.existsById(productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.getOptions(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("[Unit] add option test")
    class addOptionTest {

        @BeforeEach
        void setUp() {
            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            when(optionRepository.save(option))
                .thenReturn(option);
        }

        @Test
        @DisplayName("success")
        void success() {
            //then
            assertDoesNotThrow(() -> optionService.addOption(productId, optionDTO));
            verify(optionRepository, times(1)).save(option);
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.empty());
            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option already exists error")
        void optionAlreadyExistsError() {
            //when
            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(true);

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_ALREADY_EXISTS);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionNameAllowedCharacterMethodSource.class)
        @DisplayName("option name allowed character error")
        void optionNameAllowedCharacterError(String optionName) {
            //given
            optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NAME_ALLOWED_CHARACTER);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionNameLengthErrorMethodSource.class)
        @DisplayName("option name length error")
        void optionNameLengthError(String optionName) {
            //given
            optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NAME_LENGTH);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionQuantitySizeError.class)
        @DisplayName("option quantity size error")
        void optionQuantitySizeError(int quantity) {
            //given
            optionDTO = new OptionDTO(
                1L,
                "option-1",
                quantity
            );

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_QUANTITY_SIZE);
        }
    }

    @Nested
    @DisplayName("[Unit] update option test")
    class updateOptionTest {

        @BeforeEach
        void setUp() {
            //given
            optionDTO = new OptionDTO(
                1L,
                "update-option",
                2
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            when(optionRepository.save(option))
                .thenReturn(option);
        }

        @Test
        @DisplayName("success")
        void success() {
            //then
            assertAll(
                () -> assertDoesNotThrow(() -> optionService.updateOption(productId, optionDTO)),
                () -> assertEquals(optionDTO.getId(), option.getId()),
                () -> assertEquals(optionDTO.getName(), option.getName()),
                () -> assertEquals(optionDTO.getQuantity(), option.getQuantity())
            );

            verify(optionRepository, times(1)).save(option);
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //when
            when(productRepository.existsById(productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option not found error")
        void optionNotFoundError() {
            //when
            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.empty());

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NOT_FOUND);
        }

        @Test
        @DisplayName("option already exists error")
        void optionAlreadyExistsError() {
            //when
            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(true);

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_ALREADY_EXISTS);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionNameAllowedCharacterMethodSource.class)
        @DisplayName("option name allowed character error")
        void optionNameAllowedCharacterError(String optionName) {
            //given
            optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NAME_ALLOWED_CHARACTER);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionNameLengthErrorMethodSource.class)
        @DisplayName("option name length error")
        void optionNameLengthError(String optionName) {
            //given
            optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NAME_LENGTH);
        }

        @ParameterizedTest
        @ArgumentsSource(OptionQuantitySizeError.class)
        @DisplayName("option quantity size error")
        void optionQuantitySizeError(int quantity) {
            //given
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                quantity
            );

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_QUANTITY_SIZE);
        }
    }

    @Nested
    @DisplayName("[Unit] delete option test")
    class deleteOptionTest {

        private long optionId;

        @BeforeEach
        void setUp() {
            //given
            optionId = 1L;

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionId))
                .thenReturn(Optional.of(option));

            doNothing().when(optionRepository)
                .delete(option);
        }

        @Test
        @DisplayName("success")
        void success() {
            //then
            assertDoesNotThrow(() -> optionService.deleteOption(productId, optionId));
            verify(optionRepository, times(1)).delete(option);
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //when
            when(productRepository.existsById(productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.deleteOption(productId, optionId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option not found error")
        void optionNotFoundError() {
            //when
            when(optionRepository.findById(optionId))
                .thenReturn(Optional.empty());

            //then
            assertThatThrownBy(() -> optionService.deleteOption(productId, optionId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NOT_FOUND);
        }
    }
}
