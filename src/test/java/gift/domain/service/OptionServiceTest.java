package gift.domain.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.OptionAddRequest;
import gift.domain.dto.request.OptionUpdateRequest;
import gift.domain.entity.Category;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.exception.badRequest.OptionQuantityOutOfRangeException;
import gift.domain.exception.badRequest.OptionUpdateActionInvalidException;
import gift.domain.exception.conflict.OptionAlreadyExistsInProductException;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.exception.notFound.OptionNotIncludedInProductOptionsException;
import gift.domain.repository.OptionRepository;
import gift.global.WebConfig.Constants.Domain;
import gift.utilForTest.MockObjectSupplier;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    private Option option;
    private Category category;

    @BeforeEach
    void beforeEach() {
        option =  MockObjectSupplier.get(Option.class);
        category = MockObjectSupplier.get(Category.class);
    }

    @Test
    @DisplayName("[UnitTest] 옵션 목록 조회")
    void getAllOptions() {
        given(optionRepository.findAll()).willReturn(List.of());
        optionService.getAllOptions();
        then(optionRepository).should(times(1)).findAll();
    }

    @Test
    @DisplayName("[UnitTest] ID로 옵션 찾기 성공")
    void getOptionById() {
        given(optionRepository.findById(any())).willReturn(Optional.of(option));
        optionService.getOptionById(option.getId());
        then(optionRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] ID로 옵션 찾기 실패")
    void getOptionById_fail() {
        given(optionRepository.findById(any())).willReturn(Optional.empty());
        assertThatThrownBy(() -> optionService.getOptionById(option.getId()))
            .isInstanceOf(OptionNotFoundException.class);
        then(optionRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest] 옵션 추가")
    void addOption() {
        Product product = getProductOfHasManyOptions();
        OptionAddRequest request = new OptionAddRequest("new option", 500);
        given(optionRepository.save(any())).willReturn(request.toEntity(product));

        optionService.addOption(product, request);

        then(optionRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 추가 - 이름 중복")
    void addOption_duplicateOptionName() {
        Product product = getProductOfHasManyOptions();
        OptionAddRequest request = new OptionAddRequest("옵션1", 500);

        assertThatThrownBy(() -> optionService.addOption(product, request))
            .isInstanceOf(OptionAlreadyExistsInProductException.class);

        then(optionRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("[UnitTest] 옵션 리스트 추가")
    void addOptions() {
        Product product = getProductOfHasManyOptions();
        List<OptionAddRequest> request = List.of(
            new OptionAddRequest("옵션4", 2500),
            new OptionAddRequest("옵션5", 3500),
            new OptionAddRequest("옵션6", 4500));

        optionService.addOptions(product, request);

        then(optionRepository).should(times(1)).saveAll(any());
        then(optionRepository).should(times(1)).findAllByProduct(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 리스트 추가 - 이름 중복")
    void addOptions_duplicateNames() {
        Product product = getProductOfHasManyOptions();
        List<List<OptionAddRequest>> requests = List.of(
            List.of( // 기존 옵션과 중복되는 경우
                new OptionAddRequest("옵션3", 2500),
                new OptionAddRequest("옵션4", 3500),
                new OptionAddRequest("옵션5", 4500)),
            List.of( // 등록하려는 옵션끼리 중복되는 경우
                new OptionAddRequest("옵션4", 2500),
                new OptionAddRequest("옵션4", 3500),
                new OptionAddRequest("옵션5", 4500)));

        for (var request: requests) {
            assertThatThrownBy(() -> optionService.addOptions(product, request))
                .isInstanceOf(OptionAlreadyExistsInProductException.class);

            then(optionRepository).should(never()).saveAll(any());
            then(optionRepository).should(never()).findAllByProduct(any());
        }
    }

    @Test
    @DisplayName("[UnitTest] 옵션 업데이트 성공")
    void updateOptionById() {
        Option toUpdate = getProductOfHasManyOptions().getOptions().get(0);
        String[] action = {"add", "subtract"};
        Integer[] quantity = {
            Domain.Option.QUANTITY_RANGE_MAX - toUpdate.getQuantity(),
            toUpdate.getQuantity() - 1};
        Integer[] expectedQuantity = {
            Domain.Option.QUANTITY_RANGE_MAX,
            Domain.Option.QUANTITY_RANGE_MIN};

        for (int i = 0; i < 2; i++) {
            Product product = getProductOfHasManyOptions();
            toUpdate = product.getOptions().get(0);
            OptionUpdateRequest request = new OptionUpdateRequest("옵션1", action[i], quantity[i]);

            given(optionRepository.findById(toUpdate.getId())).willReturn(Optional.of(toUpdate));

            Option actual = optionService.updateOptionById(product, toUpdate.getId(), request);
            assertThat(actual).isNotNull();
            assertThat(actual.getQuantity()).isEqualTo(expectedQuantity[i]);
        }
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 업데이트 - 수량이 범위를 벗어남")
    void updateOptionById_quantityOutOfRange() {
        Option toUpdateInit = getProductOfHasManyOptions().getOptions().get(0);
        String[] action = {"add", "subtract"};
        Integer[] quantity = {
            Domain.Option.QUANTITY_RANGE_MAX - toUpdateInit.getQuantity() + 1,
            toUpdateInit.getQuantity()};

        for (int i = 0; i < 2; i++) {
            Product product = getProductOfHasManyOptions();
            final Option toUpdate = product.getOptions().get(0);
            OptionUpdateRequest request = new OptionUpdateRequest("옵션1", action[i], quantity[i]);

            given(optionRepository.findById(toUpdate.getId())).willReturn(Optional.of(toUpdate));

            assertThatThrownBy(() -> optionService.updateOptionById(product, toUpdate.getId(), request))
                .isInstanceOf(OptionQuantityOutOfRangeException.class);
        }
        then(optionRepository).should(times(2)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 업데이트 - id가 상품에 속해있지 않음")
    void updateOptionById_idNotExistsInProduct() {
        Product product = getProductOfHasManyOptions();
        OptionUpdateRequest request = new OptionUpdateRequest("옵션1", "add", 30);

        assertThatThrownBy(() -> optionService.updateOptionById(product, 444L, request))
            .isInstanceOf(OptionNotIncludedInProductOptionsException.class);
        then(optionRepository).should(never()).findById(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 업데이트 - 옵션이 존재하지 않음")
    void updateOptionById_optionNotExists() {
        Product product = getProductOfHasManyOptions();
        OptionUpdateRequest request = new OptionUpdateRequest("옵션1", "add", 30);
        given(optionRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> optionService.updateOptionById(product, 1L, request))
            .isInstanceOf(OptionNotFoundException.class);
        then(optionRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 업데이트 - 옵션이름이 기존과 중복됨")
    void updateOptionById_optionNameDuplicate() {
        Product product = getProductOfHasManyOptions();
        OptionUpdateRequest request = new OptionUpdateRequest("옵션3", "add", 30);
        given(optionRepository.findById(any())).willReturn(Optional.of(product.getOptions().get(0)));

        assertThatThrownBy(() -> optionService.updateOptionById(product, 1L, request))
            .isInstanceOf(OptionAlreadyExistsInProductException.class);
        then(optionRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest/Fail] 옵션 업데이트 - action이 잘못되었음")
    void updateOptionById_invalidAction() {
        Product product = getProductOfHasManyOptions();
        OptionUpdateRequest request = new OptionUpdateRequest("옵션1", "invalidAction", 30);
        given(optionRepository.findById(any())).willReturn(Optional.of(product.getOptions().get(0)));

        assertThatThrownBy(() -> optionService.updateOptionById(product, 1L, request))
            .isInstanceOf(OptionUpdateActionInvalidException.class);
        then(optionRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("[UnitTest] 옵션 삭제")
    void deleteOptionById() {
        Product product = getProductOfHasManyOptions();
        Option toDelete = product.getOptions().get(0);
        given(optionRepository.findById(any())).willReturn(Optional.of(toDelete));

        optionService.deleteOptionById(product, toDelete.getId());

        then(optionRepository).should(times(1)).findById(any());
        then(optionRepository).should(times(1)).delete(any());
        assertThat(product.getOptions().contains(toDelete)).isFalse();
    }

    @Test
    @DisplayName("[UnitTest] 옵션 삭제 - 상품에 속하지 않은 옵션 ID")
    void deleteOptionById_notIncludedInProduct() {
        Product product = getProductOfHasManyOptions();
        Long toDelete = 444L;

        assertThatThrownBy(() -> optionService.deleteOptionById(product, toDelete))
            .isInstanceOf(OptionNotIncludedInProductOptionsException.class);

        then(optionRepository).should(never()).delete(any());
        then(optionRepository).should(never()).findById(any());
    }

    private Product getProductOfHasManyOptions() {
        Product ret = new Product("product", 5000, "image.jpg", category);
        List<Option> options = List.of(
            new Option(ret, "옵션1", 500),
            new Option(ret, "옵션2", 1000),
            new Option(ret, "옵션3", 1500));
        ReflectionTestUtils.setField(ret, "id", 1L);
        for (int i = 0; i < 3; i++) {
            ReflectionTestUtils.setField(options.get(i), "id", i + 1L);
        }
        ret.getOptions().addAll(options);
        return ret;
    }
}