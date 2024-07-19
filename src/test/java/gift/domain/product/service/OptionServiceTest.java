package gift.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import gift.domain.product.dto.OptionDto;
import gift.domain.product.entity.Category;
import gift.domain.product.entity.Option;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.OptionJpaRepository;
import gift.domain.product.repository.ProductJpaRepository;
import gift.exception.DuplicateOptionNameException;
import gift.exception.InvalidOptionInfoException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@SpringBootTest
class OptionServiceTest {

    @Autowired
    private OptionService optionService;

    @MockBean
    private OptionJpaRepository optionJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    private static final Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
    private static final Product product = new Product(1L, category, "testProduct", 10000, "https://test.com");

    @BeforeEach
    void setUp() {
        product.removeOptions();
    }

    @Test
    @DisplayName("옵션 생성 서비스 테스트")
    void create() {
        // given
        OptionDto optionDto = new OptionDto(null, "수박맛", 969);
        Option option = optionDto.toOption(product);
        option.setId(1L);

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionJpaRepository.save(any(Option.class))).willReturn(option);

        // when
        OptionDto actual = optionService.create(1L, optionDto);

        // then
        assertThat(actual).isEqualTo(OptionDto.from(option));
    }

    @Test
    @DisplayName("옵션 생성 서비스 중복 옵션 테스트")
    void create_fail() {
        // given
        product.addOption(new Option(2L, product, "자두맛", 80));
        OptionDto optionDto = new OptionDto(null, "자두맛", 969);
        Option option = optionDto.toOption(product);
        option.setId(1L);

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionJpaRepository.save(any(Option.class))).willReturn(option);

        // when & then
        assertThrows(DuplicateOptionNameException.class, () -> optionService.create(1L, optionDto));
    }

    @Test
    @DisplayName("옵션 전체 조회 서비스 테스트")
    void readAll() {
        // given
        Option option = new Option(1L, product, "사과맛", 90);
        product.addOption(option);
        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when
        List<OptionDto> actual = optionService.readAll(1L);

        // then
        assertAll(
            () -> assertThat(actual).hasSize(1),
            () -> assertThat(actual.get(0)).isEqualTo(OptionDto.from(option))
        );
    }

    @Test
    @DisplayName("옵션 수정 서비스 테스트")
    void update() {
        // given
        OptionDto optionDto = new OptionDto(1L, "수박맛", 969);
        product.addOption(optionDto.toOption(product));

        OptionDto optionUpdateDto = new OptionDto(1L, "자두맛", 90);
        Option expected = optionUpdateDto.toOption(product);

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionJpaRepository.save(any(Option.class))).willReturn(expected);

        // when
        OptionDto actual = optionService.update(1L, optionUpdateDto);

        // then
        assertThat(actual).isEqualTo(OptionDto.from(expected));
    }

    @Test
    @DisplayName("옵션 수정 서비스 중복 옵션 테스트")
    void update_fail_duplicate_name() {
        // given
        OptionDto optionDto = new OptionDto(1L, "수박맛", 969);
        product.addOption(optionDto.toOption(product));
        OptionDto optionDto2 = new OptionDto(2L, "자두맛", 80);
        product.addOption(optionDto2.toOption(product));

        OptionDto optionUpdateDto = new OptionDto(1L, "자두맛", 90);
        Option expected = optionUpdateDto.toOption(product);

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionJpaRepository.save(any(Option.class))).willReturn(expected);

        // when & then
        assertThrows(DuplicateOptionNameException.class, () -> optionService.update(1L, optionUpdateDto));
    }

    @Test
    @DisplayName("옵션 수정 서비스 존재하지 않는 옵션 테스트")
    void update_fail_wrong_id() {
        // given
        OptionDto optionDto = new OptionDto(1L, "수박맛", 969);
        product.addOption(optionDto.toOption(product));

        OptionDto optionUpdateDto = new OptionDto(2L, "자두맛", 90);
        Option expected = optionUpdateDto.toOption(product);

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionJpaRepository.save(any(Option.class))).willReturn(expected);

        // when & then
        assertThrows(InvalidOptionInfoException.class, () -> optionService.update(1L, optionUpdateDto));
    }
}