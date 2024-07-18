package gift.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.constants.ErrorMessage;
import gift.dto.CategoryDto;
import gift.dto.OptionDto;
import gift.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/truncateIdentity.sql")
class OptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        CategoryDto categoryDto = new CategoryDto(null, "생일 선물", "노랑", "http", "생일 선물 카테고리");
        String category = new ObjectMapper().writeValueAsString(categoryDto);
        mockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(category));

        ProductResponse productDto = new ProductResponse(null, "케잌", 50000L, "http", 1L, "생일 선물");
        String product = new ObjectMapper().writeValueAsString(productDto);
        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(product));
    }

    @Test
    @DisplayName("옵션 추가 테스트")
    void addOption() throws Exception {
        OptionDto optionDto = new OptionDto(null, "초코 케익", 30, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("옵션 수정 테스트")
    void editOption() throws Exception {
        addOption();
        OptionDto optionDto = new OptionDto(1L, "초코 케익", 15, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(put("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("옵션 삭제 테스트")
    void deleteOption() throws Exception {
        addOption();
        mockMvc.perform(delete("/api/products/product/1/options/1"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("옵션 이름 중복 실패 테스트")
    void optionDuplicate() throws Exception {
        addOption();
        OptionDto optionDto = new OptionDto(null, "초코 케익", 15, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.OPTION_NAME_ALREADY_EXISTS_MSG));
    }

    @Test
    @DisplayName("옵션 이름 50자 초과 실패 테스트")
    void optionNameLength() throws Exception {
        OptionDto optionDto = new OptionDto(null,
            "012345678901234567890123456789012345678901234567890", 15, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.OPTION_NAME_INVALID_LENGTH_MSG));
    }

    @Test
    @DisplayName("옵션 이름 null 실패 테스트")
    void optionNameNull() throws Exception {
        OptionDto optionDto = new OptionDto(null, null, 15, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.OPTION_NAME_NOT_BLANK_MSG));
    }

    @ParameterizedTest
    @DisplayName("옵션 이름 특수문자 실패 테스트")
    @ValueSource(strings = {"생일선물!", "카카오{}", "kakao@", "talk^^", "mod%"})
    void optionNamePattern(String name) throws Exception {
        OptionDto optionDto = new OptionDto(null, name, 15, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.OPTION_NAME_INVALID_PATTERN_MSG));
    }

    @ParameterizedTest
    @DisplayName("옵션 수량 실패 테스트")
    @ValueSource(ints = {0, 100_000_000, 100_000_001})
    void optionQuantity(int quantity) throws Exception {
        OptionDto optionDto = new OptionDto(null, "생일 선물", quantity, 1L);
        String option = new ObjectMapper().writeValueAsString(optionDto);

        mockMvc.perform(post("/api/products/product/1/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(option))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.OPTION_QUANTITY_INVALID_MSG));
    }
}