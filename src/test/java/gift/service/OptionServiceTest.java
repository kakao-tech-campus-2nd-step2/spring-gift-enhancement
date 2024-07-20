package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import gift.administrator.category.Category;
import gift.administrator.option.Option;
import gift.administrator.option.OptionDTO;
import gift.administrator.option.OptionRepository;
import gift.administrator.option.OptionService;
import gift.administrator.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class OptionServiceTest {

    private OptionService optionService;
    private final OptionRepository optionRepository = mock(OptionRepository.class);
    private Category category;
    private Option option;
    private Option option1;
    private OptionDTO expected;
    private OptionDTO expected1;
    private Product product;

    @BeforeEach
    void beforeEach() {
        optionService = new OptionService(optionRepository);
        category = new Category("상품권", null, null, null);
        option = new Option(1L, "L", 3, null);
        List<Option> options = new ArrayList<>(List.of(option));
        product = new Product(1L, "라이언", 1000, "image.jpg", category, options);
        option1 = new Option(2L, "XL", 5, null);
        option.setProduct(product);
        option1.setProduct(product);
        expected = new OptionDTO(1L, "L", 3, product.getId());
        expected1 = new OptionDTO(2L, "XL", 5, product.getId());
    }

    @Test
    @DisplayName("옵션 전체 조회 테스트")
    void getAllOptions() {
        //given
        given(optionRepository.findAll()).willReturn(List.of(option, option1));

        //when
        List<OptionDTO> actual = optionService.getAllOptions();

        //then
        assertThat(actual).hasSize(2);
        assertThat(actual)
            .extracting(OptionDTO::getName, OptionDTO::getQuantity, OptionDTO::getProductId)
            .containsExactly(
                tuple(expected.getName(), expected.getQuantity(), expected.getProductId()),
                tuple(expected1.getName(), expected1.getQuantity(), expected1.getProductId())
            );
    }

    @Test
    @DisplayName("상품 아이디로 옵션 전체 조회 테스트")
    void getAllOptionsByProductId() {
        //given
        given(optionRepository.findAllByProductId(1L)).willReturn(List.of(option, option1));

        //when
        List<OptionDTO> actual = optionService.getAllOptionsByProductId(1L);

        //then
        assertThat(actual).hasSize(2);
        assertThat(actual)
            .extracting(OptionDTO::getName, OptionDTO::getQuantity, OptionDTO::getProductId)
            .containsExactly(
                tuple(expected.getName(), expected.getQuantity(), expected.getProductId()),
                tuple(expected1.getName(), expected1.getQuantity(), expected1.getProductId())
            );
    }

    @Test
    @DisplayName("옵션 아이디로 옵션 전체 조회 테스트")
    void getAllOptionsByOptionId() {
        //given
        given(optionRepository.findAllById(List.of(1L, 2L))).willReturn(List.of(option, option1));

        //when
        List<OptionDTO> actual = optionService.getAllOptionsByOptionId(List.of(1L, 2L));

        //then
        assertThat(actual).hasSize(2);
        assertThat(actual)
            .extracting(OptionDTO::getName, OptionDTO::getQuantity, OptionDTO::getProductId)
            .containsExactly(
                tuple(expected.getName(), expected.getQuantity(), expected.getProductId()),
                tuple(expected1.getName(), expected1.getQuantity(), expected1.getProductId())
            );
    }

    @Test
    @DisplayName("옵션 아이디와 상품 아이디로 존재 여부 확인 시 존재함")
    void existsByOptionIdAndProductId() {
        //given
        given(optionRepository.existsByIdAndProductId(1L, 1L)).willReturn(true);

        //when
        boolean actual = optionService.existsByOptionIdAndProductId(1L, 1L);

        //then
        assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName("옵션 아이디와 상품 아이디로 존재 여부 확인 시 존재하지 않음")
    void notExistsByOptionIdAndProductId() {
        //given
        given(optionRepository.existsByIdAndProductId(1L, 1L)).willReturn(false);

        //when
        boolean actual = optionService.existsByOptionIdAndProductId(1L, 1L);

        //then
        assertThat(actual).isEqualTo(false);
    }

    @Test
    @DisplayName("옵션 아이디와 상품 아이디로 존재 여부 확인 시 존재함")
    void findOptionById() throws NotFoundException {
        //given
        given(optionRepository.findById(1L)).willReturn(Optional.ofNullable(option));

        //when
        OptionDTO actual = optionService.findOptionById(1L);

        //then
        assertThat(actual)
            .extracting(OptionDTO::getName, OptionDTO::getQuantity, OptionDTO::getProductId)
            .containsExactly(expected.getName(), expected.getQuantity(), expected.getProductId());
    }

    @Test
    @DisplayName("옵션 아이디와 상품 아이디로 존재 여부 확인 시 존재하지 않음")
    void findOptionByIdNotFound() throws NotFoundException {
        //given
        given(optionRepository.findById(1L)).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> optionService.findOptionById(1L)).isInstanceOf(
            NotFoundException.class);
    }

    @Test
    @DisplayName("옵션 저장")
    void addOption(){
        //given
        given(optionRepository.save(any())).willReturn(option);

        //when
        OptionDTO actual = optionService.addOption(OptionDTO.fromOption(option), product);

        //then
        then(optionRepository).should().save(any());
        assertThat(actual)
            .extracting(OptionDTO::getName, OptionDTO::getQuantity, OptionDTO::getProductId)
            .containsExactly(expected.getName(), expected.getQuantity(), expected.getProductId());
    }

    @Test
    @DisplayName("상품 아이디로 옵션 삭제")
    void deleteOptionByProductId(){
        //given

        //when
        optionService.deleteOptionByProductId(1L);

        //then
        then(optionRepository).should().deleteByProductId(1L);
    }

    @Test
    @DisplayName("옵션 아이디로 옵션 삭제")
    void deleteOptionByOptionId() throws NotFoundException {
        //given
        given(optionRepository.existsById(1L)).willReturn(true);
        given(optionRepository.findById(1L)).willReturn(Optional.ofNullable(option));

        //when
        optionService.deleteOptionByOptionId(1L);

        //then
        then(optionRepository).should().deleteById(1L);
    }

    @Test
    @DisplayName("옵션 아이디로 옵션 삭제시 존재하지 않는 아이디로 삭제 시도")
    void deleteOptionByOptionIdIdNotFound() throws NotFoundException {
        //given
        given(optionRepository.existsById(1L)).willReturn(false);

        //when

        //then
        assertThatIllegalArgumentException().isThrownBy(
            () -> optionService.deleteOptionByOptionId(1L)).withMessage("없는 아이디입니다.");
    }

    @Test
    @DisplayName("옵션 수량 차감 성공")
    void subtractOptionQuantity() throws NotFoundException {
        //given
        given(optionRepository.findById(1L)).willReturn(Optional.ofNullable(option));
        Option expected = new Option(1L, "L", 1, null);

        //when
        Option actual = optionService.subtractOptionQuantity(1L, 2);

        //then
        assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity());
    }

    @Test
    @DisplayName("옵션 수량 차감 시도 중 아이디 찾기 실패")
    void subtractOptionQuantityIdNotFound(){
        //given
        given(optionRepository.findById(1L)).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> optionService.subtractOptionQuantity(1L, 2)).isInstanceOf(
            NotFoundException.class);
    }

    @Test
    @DisplayName("옵션 수량 차감 시도 중 옵션 수량보다 차감하려는 수량이 더 많을 때")
    void subtractOptionQuantityNotEnoughQuantity(){
        //given
        given(optionRepository.findById(1L)).willReturn(Optional.ofNullable(option));

        //when

        //then
        assertThatIllegalArgumentException().isThrownBy(
            () -> optionService.subtractOptionQuantity(1L, 4)).withMessage("옵션의 수량이 부족합니다.");
    }
}
