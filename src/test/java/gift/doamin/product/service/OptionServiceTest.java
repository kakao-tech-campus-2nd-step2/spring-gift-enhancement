package gift.doamin.product.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import gift.doamin.category.entity.Category;
import gift.doamin.product.dto.OptionForm;
import gift.doamin.product.entity.Product;
import gift.doamin.product.exception.ProductNotFoundException;
import gift.doamin.product.repository.JpaProductRepository;
import gift.doamin.product.repository.OptionRepository;
import gift.doamin.user.entity.User;
import gift.doamin.user.entity.UserRole;
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
    void 상품이_없는_경우() {
        given(productRepository.findById(any())).willReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ProductNotFoundException.class)
            .isThrownBy(() -> optionService.create(1L, new OptionForm("option", 1)));
    }

    @Test
    void 상품의_옵션_이름이_중복되는_경우() {
        User user = new User("user@google.com", "pw", "user", UserRole.SELLER);
        Category category = new Category("category");
        given(productRepository.findById(any()))
            .willReturn(Optional.of(new Product(user, category, "product", 1000, "image")));
        given(optionRepository.existsByProductIdAndName(any(), any()))
            .willReturn(true);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> optionService.create(1L, new OptionForm("option", 1)));
    }
}