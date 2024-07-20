package gift.doamin.product.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import gift.doamin.category.entity.Category;
import gift.doamin.product.dto.OptionForm;
import gift.doamin.product.entity.Option;
import gift.doamin.product.entity.Product;
import gift.doamin.product.exception.ProductNotFoundException;
import gift.doamin.product.repository.JpaProductRepository;
import gift.doamin.product.repository.OptionRepository;
import gift.doamin.user.entity.User;
import gift.doamin.user.entity.UserRole;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionServiceTest {

    private JpaProductRepository productRepository = mock(JpaProductRepository.class);
    private OptionRepository optionRepository = mock(OptionRepository.class);
    private OptionService optionService;

    @BeforeEach
    void setup() {
        optionService = new OptionService(productRepository, optionRepository);
    }

    @Test
    void 저장_상품이_없는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ProductNotFoundException.class)
            .isThrownBy(() -> optionService.create(1L, createOptionFrom()));
    }

    @Test
    void 저장_옵션_이름이_중복되는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.of(createProduct()));
        given(optionRepository.existsByProductIdAndName(any(), any())).willReturn(true);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> optionService.create(1L, createOptionFrom()));
    }

    @Test
    void 저장_성공() {
        given(productRepository.findById(any())).willReturn(Optional.of(createProduct()));
        given(optionRepository.existsByProductIdAndName(any(), any())).willReturn(false);

        optionService.create(1L, createOptionFrom());

        then(optionRepository).should().save(any(Option.class));
    }

    @Test
    void 수정_상품이_없는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ProductNotFoundException.class)
            .isThrownBy(() -> optionService.update(1L, 1L, createOptionFrom()));
    }

    @Test
    void 수정_옵션이_없는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.of(createProduct()));
        given(optionRepository.findById(any())).willReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(() -> optionService.update(1L, 1L, createOptionFrom()));
    }

    @Test
    void 수정_옵션_이름이_중복되는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.of(createProduct()));
        given(optionRepository.findById(any())).willReturn(Optional.of(createOption()));
        given(optionRepository.existsByProductIdAndName(any(), any())).willReturn(true);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> optionService.update(1L, 1L, createOptionFrom()));
    }

    @Test
    void 수정_성공() {
        given(productRepository.findById(any())).willReturn(Optional.of(createProduct()));
        given(optionRepository.findById(any())).willReturn(Optional.of(createOption()));
        given(optionRepository.existsByProductIdAndName(any(), any())).willReturn(false);

        optionService.update(1L, 1L, createOptionFrom());

        then(optionRepository).should().save(any(Option.class));
    }


    Product createProduct() {
        User user = new User("user@google.com", "pw", "user", UserRole.SELLER);
        Category category = new Category("category");

        return new Product(user, category, "product", 1000, "image");
    }

    Option createOption() {
        return new Option("option", 1);
    }

    OptionForm createOptionFrom() {
        return new  OptionForm("option", 1);
    }
}