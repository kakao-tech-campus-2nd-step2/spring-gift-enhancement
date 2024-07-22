package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import gift.domain.ProductOption;
import gift.domain.ProductOption.Builder;
import gift.repository.ProductOptionRepository;
import gift.web.dto.request.productoption.CreateProductOptionRequest;
import gift.web.dto.request.productoption.UpdateProductOptionRequest;
import gift.web.dto.response.productoption.CreateProductOptionResponse;
import gift.web.dto.response.productoption.ReadAllProductOptionsResponse;
import gift.web.dto.response.productoption.UpdateProductOptionResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {

    @InjectMocks
    private ProductOptionService productOptionService;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Test
    @DisplayName("상품 옵션 생성 요청이 정상적일 때, 상품 옵션을 성공적으로 생성합니다.")
    void createOption() {
        //given
        Long productId = 1L;
        CreateProductOptionRequest request = new CreateProductOptionRequest("optionName", 1000);
        given(productOptionRepository.save(any())).willReturn(request.toEntity(productId));

        //when
        CreateProductOptionResponse response = productOptionService.createOption(productId, request);

        //then
        assertAll(
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getStock()).isEqualTo(request.getStock())
        );
    }

    @Test
    @DisplayName("상품 옵션 조회 요청이 정상적일 때, 상품 옵션을 성공적으로 조회합니다.")
    void readAllOptions() {
        //given
        ProductOption option01 = new Builder().productId(1L).name("optionName").stock(1000).build();
        ProductOption option02 = new Builder().productId(1L).name("optionName").stock(1000).build();
        given(productOptionRepository.findAllByProductId(1L)).willReturn(List.of(option01, option02));

        //when
        ReadAllProductOptionsResponse response = productOptionService.readAllOptions(1L);

        //then
        assertAll(
            () -> assertThat(response.getOptions().get(0).getName()).isEqualTo(option01.getName()),
            () -> assertThat(response.getOptions().get(0).getStock()).isEqualTo(option01.getStock()),
            () -> assertThat(response.getOptions().get(1).getName()).isEqualTo(option02.getName()),
            () -> assertThat(response.getOptions().get(1).getStock()).isEqualTo(option02.getStock())
        );
    }

    @Test
    @DisplayName("상품 옵션 수정 요청이 정상적일 때, 상품 옵션을 성공적으로 수정합니다.")
    void updateOption() {
        //given
        Long productId = 1L;
        Long optionId = 1L;
        UpdateProductOptionRequest request = new UpdateProductOptionRequest("optionName", 1000);
        ProductOption option = new Builder().productId(productId).name("optionName").stock(1000).build();
        given(productOptionRepository.findById(optionId)).willReturn(Optional.of(option));

        //when
        UpdateProductOptionResponse response = productOptionService.updateOption(productId, optionId, request);

        //then
        assertAll(
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getStock()).isEqualTo(request.getStock())
        );
    }

    @Test
    @DisplayName("상품 옵션 삭제 요청이 정상적일 때, 상품 옵션을 성공적으로 삭제합니다.")
    void deleteOption() {
        //given
        Long optionId = 1L;

        //when
        given(productOptionRepository.findById(optionId)).willReturn(Optional.of(new ProductOption.Builder().build()));

        //then
        assertDoesNotThrow(() -> productOptionService.deleteOption(optionId));
    }

    @Test
    @DisplayName("상품 옵션 전체 삭제 요청이 정상적일 때, 상품 옵션을 성공적으로 전체 삭제합니다.")
    void deleteAllOptionsByProductId() {
        //given
        Long productId = 1L;

        //when
        //then
        assertDoesNotThrow(() -> productOptionService.deleteAllOptionsByProductId(productId));
    }
}