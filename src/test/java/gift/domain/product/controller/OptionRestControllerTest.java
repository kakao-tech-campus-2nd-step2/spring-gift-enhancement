package gift.domain.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.product.dto.OptionDto;
import gift.domain.product.service.OptionService;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class OptionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OptionService optionService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String DEFAULT_URL = "/api/products/1/options";

    @Test
    @DisplayName("옵션 생성에 성공하는 경우")
    void create_success() throws Exception {
        // given
        OptionDto optionDto = new OptionDto(null, "01 [Best] 시어버터 핸드 & 시어 스틱 립 밤", 969);
        String jsonContent = objectMapper.writeValueAsString(optionDto);

        OptionDto expected = new OptionDto(1L, "01 [Best] 시어버터 핸드 & 시어 스틱 립 밤", 969);
        given(optionService.create(anyLong(), any(OptionDto.class))).willReturn(expected);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)))
            .andDo(print());
    }

    @Test
    @DisplayName("옵션 생성에 실패하는 경우 - 빈 이름")
    void create_fail_null_name_error() throws Exception {
        // given
        OptionDto optionDto = new OptionDto(null, null, 969);
        String jsonContent = objectMapper.writeValueAsString(optionDto);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name", Is.is("옵션 이름은 필수 입력 필드이며 공백으로만 구성될 수 없습니다.")));
    }

    @Test
    @DisplayName("옵션 생성에 실패하는 경우 - 옵션 이름 50자 초과")
    void create_fail_name_size_error() throws Exception {
        // given
        OptionDto optionDto = new OptionDto(null, "옵션이름".repeat(50), 969);
        String jsonContent = objectMapper.writeValueAsString(optionDto);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name", Is.is("옵션 이름은 50자를 초과할 수 없습니다.")));
    }

    @Test
    @DisplayName("옵션 생성에 실패하는 경우 - 불가능한 특수 문자")
    void create_fail_special_char_error() throws Exception {
        // given
        OptionDto optionDto = new OptionDto(null, "이름#", 969);
        String jsonContent = objectMapper.writeValueAsString(optionDto);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name", Is.is("(,),[,],+,-,&,/,_ 외의 특수 문자는 사용이 불가능합니다.")));
    }

    @Test
    @DisplayName("옵션 생성에 실패하는 경우 - 수량 범위 벗어남")
    void create_fail_quantity_range_error() throws Exception {
        // given
        OptionDto optionDto = new OptionDto(null, "이름", -1);
        String jsonContent = objectMapper.writeValueAsString(optionDto);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.quantity", Is.is("옵션 수량은 1 이상 100,000,000 이하여야 합니다.")));
    }

    @Test
    @DisplayName("옵션을 전체 조회하는 경우")
    void readAll() throws Exception {
        // given
        List<OptionDto> optionDtos = List.of(
            new OptionDto(1L, "빨간색", 20),
            new OptionDto(2L, "하늘색", 50)
        );
        given(optionService.readAll(anyLong())).willReturn(optionDtos);

        // when & then
        mockMvc.perform(get(DEFAULT_URL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(optionDtos)))
            .andDo(print());
    }
}