package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import gift.dto.OptionRequestDTO;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionRepositoryKeeperService optionRepositoryKeeperService;

    OptionService optionService;

    private Product product1;
    private Option option1;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        optionService = new OptionService(optionRepository, optionRepositoryKeeperService, productRepository);

        product1 = new Product("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
                new Category("테스트1", "#000001", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "테스트 1")
                );
        option1 = new Option(product1, "옵션1", 100);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getOneProductIdAllOptions() {
        given(optionService.getOneProductIdAllOptions(any())).willReturn(new ArrayList<>());
        assertThat(optionService.getOneProductIdAllOptions(any())).isNotNull();
    }

    @Test
    void addOption() {
        given(optionRepository.save(any())).willReturn(option1);
        given(productRepository.findById(any())).willReturn(Optional.of(product1));
        doNothing().when(optionRepositoryKeeperService).checkUniqueOptionName(any(), any());

        assertThatNoException().isThrownBy(() -> optionService.addOption(1L,
                new OptionRequestDTO(1L, 1L, option1.getName(), option1.getQuantity())));
    }

    @Test
    void updateOption() {
        given(productRepository.findById(any())).willReturn(Optional.of(product1));
        given(optionRepository.findById((Long) any())).willReturn(Optional.of(option1));
        doNothing().when(optionRepositoryKeeperService).checkUniqueOptionName(any(), any());

        assertThatNoException().isThrownBy(() -> optionService.updateOption(1L, 1L,
                new OptionRequestDTO(1L, 1L, option1.getName(), option1.getQuantity())));
    }

    @Test
    void deleteOption() {
        given(optionRepository.findByIdAndProductId(any(), any())).willReturn(Optional.of(option1));
        given(optionRepository.countByProduct(any())).willReturn(2);

        doNothing().when(optionRepository).delete(any());
        assertThatNoException().isThrownBy(() -> optionService.deleteOption(1L, 1L));
    }
}