package gift.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import java.util.Optional;
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
}