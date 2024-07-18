package gift.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

import gift.entity.Category;
import gift.entity.Product;
import gift.repository.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

class OptionRepositoryKeeperServiceTest {
    @Mock
    OptionRepository optionRepository;
    OptionRepositoryKeeperService optionRepositoryKeeperService;

    Category category;

    Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        optionRepositoryKeeperService = new OptionRepositoryKeeperService(optionRepository);
        category = new Category("테스트1", "#000001", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "테스트 1");
        product = new Product("테스트", 10000, "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", category);
    }

    @Test
    void checkUniqueOptionName() {
        given(optionRepository.existsByProductAndName(any(), any())).willReturn(true);
        assertThatThrownBy(() -> optionRepositoryKeeperService.checkUniqueOptionName(product, "테스트")).isInstanceOf(
                DataIntegrityViolationException.class);

        given(optionRepository.existsByProductAndName(any(), any())).willReturn(false);
        assertThatNoException().isThrownBy(() -> optionRepositoryKeeperService.checkUniqueOptionName(product, "테스트"));
    }
}