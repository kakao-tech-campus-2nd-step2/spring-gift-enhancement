package gift.option;

import static gift.exception.ErrorMessage.OPTION_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.OPTION_NAME_ALLOWED_CHARACTER;
import static gift.exception.ErrorMessage.OPTION_NAME_LENGTH;
import static gift.exception.ErrorMessage.OPTION_NOT_FOUND;
import static gift.exception.ErrorMessage.OPTION_QUANTITY_SIZE;
import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import gift.category.Category;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
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

    @Nested
    @DisplayName("[Unit] get options test")
    class getOptionsTest {

        @Test
        @DisplayName("success")
        void success() {
            //given
            long productId = 1L;
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            List<Option> optionReturnValues = List.of(
                new Option(1L, "option-1", 1, product),
                new Option(2L, "option-2", 2, product),
                new Option(3L, "option-3", 3, product)
            );
            List<OptionDTO> expected = List.of(
                new OptionDTO(1L, "option-1", 1),
                new OptionDTO(2L, "option-2", 2),
                new OptionDTO(3L, "option-3", 3)
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findAllByProductId(productId))
                .thenReturn(optionReturnValues);

            List<OptionDTO> actual = optionService.getOptions(productId);

            //then
            assertEquals(actual, expected);
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //given
            long productId = 1L;
            List<Option> optionReturnValues = List.of();

            //when
            when(productRepository.existsById(productId))
                .thenReturn(false);

            when(optionRepository.findAllByProductId(productId))
                .thenReturn(optionReturnValues);

            //then
            assertThatThrownBy(() -> optionService.getOptions(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("[Unit] add option test")
    class addOptionTest {

        @Test
        @DisplayName("success")
        void success() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "option-1",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            when(optionRepository.save(optionDTO.toEntity(product)))
                .thenReturn(optionDTO.toEntity(product));

            //then
            assertDoesNotThrow(
                () -> optionService.addOption(productId, optionDTO)
            );
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "option-1",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            when(optionRepository.save(optionDTO.toEntity(product)))
                .thenReturn(optionDTO.toEntity(product));

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option already exists error")
        void optionAlreadyExistsError() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "option-1",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(true);

            when(optionRepository.save(optionDTO.toEntity(product)))
                .thenReturn(optionDTO.toEntity(product));

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "option-1",
                quantity
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.addOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_QUANTITY_SIZE);
        }
    }

    @Nested
    @DisplayName("[Unit] update option test")
    class updateOptionTest {

        @Test
        @DisplayName("success")
        void success() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "update-option",
                1,
                product
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

            //then
            assertDoesNotThrow(
                () -> optionService.updateOption(productId, optionDTO)
            );
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "option",
                1,
                product
            );

            //when
            when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option not found error")
        void optionNotFoundError() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                1
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.empty());

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NOT_FOUND);
        }

        @Test
        @DisplayName("option already exists error")
        void optionAlreadyExistsError() {
            //given
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "option",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "option-1",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                optionName,
                1
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "option-1",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

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
            long productId = 1L;
            OptionDTO optionDTO = new OptionDTO(
                1L,
                "update-option",
                quantity
            );
            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );
            Option option = new Option(
                1L,
                "option-1",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionDTO.getId()))
                .thenReturn(Optional.of(option));

            when(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId))
                .thenReturn(false);

            //then
            assertThatThrownBy(() -> optionService.updateOption(productId, optionDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_QUANTITY_SIZE);
        }
    }

    @Nested
    @DisplayName("[Unit] delete option test")
    class deleteOptionTest {

        @Test
        @DisplayName("success")
        void success() {
            //given
            long productId = 1L;
            long optionId = 1L;

            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            Option option = new Option(
                1L,
                "option",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionId))
                .thenReturn(Optional.of(option));

            doNothing().when(optionRepository)
                .delete(option);

            //then
            assertDoesNotThrow(() -> optionService.deleteOption(productId, optionId));
        }

        @Test
        @DisplayName("product not found error")
        void productNotFoundError() {
            //given
            long productId = 1L;
            long optionId = 1L;

            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            Option option = new Option(
                1L,
                "option",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(false);

            when(optionRepository.findById(optionId))
                .thenReturn(Optional.of(option));

            doNothing().when(optionRepository)
                .delete(option);

            //then
            assertThatThrownBy(() -> optionService.deleteOption(productId, optionId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NOT_FOUND);
        }

        @Test
        @DisplayName("option not found error")
        void optionNotFoundError() {
            //given
            long productId = 1L;
            long optionId = 1L;

            Product product = new Product(
                1L,
                "product",
                1,
                "imageUrl",
                new Category(1L, "category")
            );

            Option option = new Option(
                1L,
                "option",
                1,
                product
            );

            //when
            when(productRepository.existsById(productId))
                .thenReturn(true);

            when(optionRepository.findById(optionId))
                .thenReturn(Optional.empty());

            doNothing().when(optionRepository)
                .delete(option);

            //then
            assertThatThrownBy(() -> optionService.deleteOption(productId, optionId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OPTION_NOT_FOUND);
        }
    }
}
