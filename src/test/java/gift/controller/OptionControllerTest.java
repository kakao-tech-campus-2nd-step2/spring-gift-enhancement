package gift.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.CategoryDto;
import gift.dto.OptionDto;
import gift.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

        ProductDto productDto = new ProductDto(null, "케잌", 50000L, "http", 1L, "생일 선물");
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
}