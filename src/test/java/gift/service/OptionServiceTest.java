package gift.service;

import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.OptionRequest;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("옵션 저장 테스트[실패] - 잘못된 product id")
    void save() {
        // given
        String name = "name";
        int quantity = 2;
        Long productId = 1L;
        var request = new OptionRequest.Create(name, quantity, productId);
        given(productRepository.existsById(eq(productId)))
                .willReturn(false);

        // when
        // then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(()->optionService.save(request));
    }

    @Test
    @DisplayName("옵션 업데이트 테스트[실패] - 잘못된 옵션 id")
    void updateById() {
        // given
        Long id = 1L;
        String name = "name";
        int quantity = 2;
        Long productId = 1L;
        var request = new OptionRequest.Update(id, name, quantity, productId);
        given(optionRepository.existsById(eq(id)))
                .willReturn(false);
        // when
        // then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> optionService.updateById(request));
    }

    @Test
    @DisplayName("옵션 최소 개수 테스트[실패] - 옵션이 1개 미만")
    void deleteByIdAndProductId() {
        Long id = 1L;
        Long productId = 1L;
        given(optionRepository.findAllByProductIdFetchJoin(eq(productId)))
                .willReturn(new ArrayList<>());

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->optionService.deleteByIdAndProductId(id, productId));
    }
}